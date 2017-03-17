package com.internship.pbt.bizarechat.data.repository;


import android.util.Log;

import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.data.datamodel.MessageModelDao;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.datamodel.response.MessagesResponse;
import com.internship.pbt.bizarechat.data.net.requests.MarkMessagesAsReadRequest;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;
import com.internship.pbt.bizarechat.logs.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DialogsDataRepository implements DialogsRepository {
    private static final String TAG = DialogsDataRepository.class.getSimpleName();

    private DialogsService dialogsService;
    private DaoSession daoSession;

    public DialogsDataRepository(DialogsService dialogsService, DaoSession daoSession) {
        this.daoSession = daoSession;
        this.dialogsService = dialogsService;
    }

    @Override
    public Observable<AllDialogsResponse> getAllDialogs(Map<String, String> parameters) {
        return Observable.fromCallable(() -> {
            AllDialogsResponse[] wrapper = new AllDialogsResponse[1];
            Exception exception = new Exception();

            dialogsService.getDialogs(UserToken.getInstance().getToken(), parameters)
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
                    .subscribe(new Subscriber<AllDialogsResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            exception.initCause(e);
                        }

                        @Override
                        public void onNext(AllDialogsResponse allDialogsResponse) {
                            DialogModelDao modelDao = daoSession.getDialogModelDao();
                            modelDao.insertOrReplaceInTx(allDialogsResponse.getDialogModels());
                            wrapper[0] = allDialogsResponse;
                        }
                    });

            if (exception.getCause() != null) {
                throw exception;
            }

            return wrapper[0];
        });
    }

    @Override
    public Observable<DialogUpdateResponseModel> updateDialog(String dialogId, DialogUpdateRequestModel updateRequestModel) {
        return dialogsService.updateDialog(UserToken.getInstance().getToken(), dialogId, updateRequestModel);
    }

    @Override
    public Observable<Response<Void>> deleteDialogForCurrentUser(String dialogId) {
        return dialogsService.deleteDialog(UserToken.getInstance().getToken(), dialogId);
    }

    @Override
    public Observable<DialogModel> createDialog(NewDialog model) {
        return dialogsService.createDialog(UserToken.getInstance().getToken(), model);
    }

    @Override
    public Observable<Map<String, Integer>> getUnreadMessagesCount() {
        return dialogsService.getUnreadMessagesCount(UserToken.getInstance().getToken());
    }

    @Override
    public Observable<List<MessageModel>> getMessages(String chatDialogId, int dialogType) {
        return Observable.fromCallable(() -> {
            List<MessageModel> messagesFromDao = daoSession.getMessageModelDao()
                    .queryBuilder()
                    .where(MessageModelDao.Properties.ChatDialogId.eq(chatDialogId))
                    .orderAsc(MessageModelDao.Properties.DateSent)
                    .list();

            if (dialogType != DialogsType.PRIVATE_CHAT) {
                getUnreadMessagesCount().flatMap(new Func1<Map<String, Integer>, Observable<MessagesResponse>>() {
                    @Override
                    public Observable<MessagesResponse> call(Map<String, Integer> unreadCount) {
                        //avoiding unnecessary request for messages
                        if (unreadCount.get("total") == 0)
                            throw new IllegalArgumentException("Unread messages count is 0");

                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("chat_dialog_id", chatDialogId);
                        parameters.put("sort_desc", "date_sent");
                        parameters.put("limit", String.valueOf(unreadCount.get("total")));
                        return dialogsService.getMessages(UserToken.getInstance().getToken(), parameters);
                    }
                }).subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate())
                        .subscribe(new Subscriber<MessagesResponse>() {
                            @Override public void onCompleted() {

                            }

                            @Override public void onError(Throwable e) {
                                if (e instanceof IllegalArgumentException) return;
                                Logger.logExceptionToFabric(e);
                                Log.d(TAG, e.getMessage(), e);
                            }

                            @Override public void onNext(MessagesResponse messagesResponse) {
                                Collections.reverse(messagesResponse.getMessageModels());
                                daoSession.getMessageModelDao().insertInTx(messagesResponse.getMessageModels());
                                messagesFromDao.addAll(messagesResponse.getMessageModels());
                            }
                        });
            }

            return messagesFromDao;
        });
    }

    @Override
    public Observable<Response<Void>> markMessagesAsRead(String dialogId) {
        return Observable.fromCallable(() -> {
            DialogModel dialog = daoSession.getDialogModelDao().queryBuilder()
                    .where(DialogModelDao.Properties.DialogId.eq(dialogId)).unique();
            dialog.setUnreadMessagesCount(0);
            daoSession.getDialogModelDao().updateInTx(dialog);

            return dialogsService.markMessagesAsRead(
                    UserToken.getInstance().getToken(),
                    new MarkMessagesAsReadRequest(1, dialogId)).execute();
        });
    }

    @Override
    public Observable<DialogModel> getPrivateDialogByUserId(long id) {
        return Observable.fromCallable(() -> {
            DialogModel resultDialog = null;
            List<DialogModel> dialogs = daoSession.getDialogModelDao().queryBuilder()
                    .where(DialogModelDao.Properties.Type.eq(DialogsType.PRIVATE_CHAT)).list();

            for (DialogModel dialog : dialogs) {
                if (dialog.getOccupantsIds().contains((int) id)) {
                    resultDialog = dialog;
                }
            }

            if (resultDialog != null) {
                return resultDialog;
            }

            Exception exception = new Exception();
            DialogModel[] wrapper = new DialogModel[1];
            createDialog(new NewDialog(DialogsType.PRIVATE_CHAT, "", String.valueOf(id), ""))
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
                    .subscribe(new Subscriber<DialogModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            exception.initCause(e);
                        }

                        @Override
                        public void onNext(DialogModel dialogModel) {
                            wrapper[0] = dialogModel;
                            daoSession.getDialogModelDao().insertInTx(dialogModel);
                        }
                    });

            if (exception.getCause() != null) {
                throw exception;
            }

            return wrapper[0];
        });
    }
}