package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DialogsDataRepository implements DialogsRepository {
    private DialogsService dialogsService;
    private DaoSession daoSession;

    public DialogsDataRepository(DialogsService dialogsService, DaoSession daoSession) {
        this.daoSession = daoSession;
        this.dialogsService = dialogsService;
    }

    @Override
    public Observable<AllDialogsResponse> getAllDialogs(Map<String, String> parameters) {
        return dialogsService.getDialogs(UserToken.getInstance().getToken(), parameters);
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
    public Observable<Map<String, Integer>> getUnreadMessagesCount(){
        return dialogsService.getUnreadMessagesCount(UserToken.getInstance().getToken());
    }

    @Override
    public Observable<DialogModel> getPrivateDialogByUserId(long id){
        return Observable.fromCallable(() -> {
            DialogModel resultDialog = null;
            List<DialogModel> dialogs = daoSession.getDialogModelDao().queryBuilder()
                    .where(DialogModelDao.Properties.Type.eq(DialogsType.PRIVATE_CHAT)).list();

            for (DialogModel dialog : dialogs) {
                if (dialog.getOccupantsIds().contains((int) id)) {
                    resultDialog = dialog;
                }
            }

            if (resultDialog != null) return resultDialog;

            Exception exception = new Exception();
            DialogModel[] wrapper = new DialogModel[1];
            createDialog(new NewDialog(DialogsType.PRIVATE_CHAT, String.valueOf(id), "", ""))
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
                    .subscribe(new Subscriber<DialogModel>() {
                        @Override public void onCompleted() {

                        }

                        @Override public void onError(Throwable e) {
                            exception.initCause(e);
                        }

                        @Override public void onNext(DialogModel dialogModel) {
                            wrapper[0] = dialogModel;
                        }
                    });

            if(exception.getCause() != null)
                throw exception;

            return wrapper[0];
        });
    }
}