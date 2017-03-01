package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.db.QueryBuilder;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllDialogsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUnreadMessagesCountUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUserByIdUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Subscriber;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView>
        implements DialogsRecyclerViewAdapter.OnDialogDeleteCallback, DialogsPresenter {
    private static final String TAG = DialogsPresenterImp.class.getSimpleName();
    private DaoSession daoSession;
    private QueryBuilder queryBuilder;
    private DialogsRecyclerViewAdapter adapter;
    private DeleteDialogUseCase deleteDialogUseCase;
    private GetPhotoUseCase photoUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private GetUnreadMessagesCountUseCase unreadMessagesCountUseCase;
    private GetAllDialogsUseCase allDialogsUseCase;
    private int dialogsType;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;

    public DialogsPresenterImp(DeleteDialogUseCase deleteDialogUseCase,
                               GetPhotoUseCase photoUseCase,
                               GetUserByIdUseCase getUserByIdUseCase,
                               GetUnreadMessagesCountUseCase unreadMessagesCountUseCase,
                               GetAllDialogsUseCase allDialogsUseCase,
                               DaoSession daoSession,
                               int dialogsType) {
        this.deleteDialogUseCase = deleteDialogUseCase;
        this.photoUseCase = photoUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.daoSession = daoSession;
        this.dialogsType = dialogsType;
        this.unreadMessagesCountUseCase = unreadMessagesCountUseCase;
        this.allDialogsUseCase = allDialogsUseCase;
        dialogs = new ArrayList<>();
        queryBuilder = QueryBuilder.getQueryBuilder(daoSession);
        dialogPhotos = new HashMap<>();
        adapter = new DialogsRecyclerViewAdapter(dialogs, dialogPhotos);
        adapter.setOnDialogDeleteCallback(this);
    }

    @Override
    public void checkConnectionProblem() {

    }

    @Override
    public void openDialog() {

    }

    public void loadDialogs() {
        getDialogsFromDao();
    }

    private List<DialogModel> getDialogsFromDao() {
        dialogs.clear();
        if (daoSession.getDialogModelDao().count() != 0) {
            List<DialogModel> buffer;
            if (dialogsType == DialogsType.PRIVATE_CHAT) {
                buffer = queryBuilder.getPrivateDialogs(DialogsType.PRIVATE_CHAT);
            } else {
                buffer = queryBuilder.getPublicDialogs(dialogsType);
            }
            dialogs.addAll(buffer);
            for (int i = 0; i < dialogs.size(); i++) {
                getAndAddPhoto(dialogs.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        return dialogs;
    }

    public DialogsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onDialogDelete(int position) {
        DialogModel model = dialogs.get(position);
        String dialogId = model.getDialogId();
        queryBuilder.removeDialog(model);

        deleteDialogUseCase.setDialogId(dialogId);
        deleteDialogUseCase.execute(new Subscriber<Response<Void>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", e.getLocalizedMessage());
            }

            @Override
            public void onNext(Response<Void> o) {

            }
        });
    }


    private void getAndAddPhoto(DialogModel dialogModel) {
        if (dialogModel.getType() == DialogsType.PRIVATE_CHAT) {
            setUserPhotoId(getOccupantIdFromPrivateDialog(dialogModel), dialogModel);
        } else {
            if (dialogModel.getPhoto() != null && !dialogModel.getPhoto().isEmpty())
                setDialogPhotos(Integer.valueOf(dialogModel.getPhoto()), dialogModel);
        }

    }

    private void setDialogPhotos(Integer blobId, DialogModel dialog) {
        photoUseCase.setBlobId(blobId);
        photoUseCase.execute(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                dialogPhotos.put(dialog.getDialogId(), bitmap);
                adapter.notifyItemChanged(dialogs.indexOf(dialog));
            }
        });
    }

    private void setUserPhotoId(Integer lastUserId, DialogModel dialog) {
        if (queryBuilder.isUserExist(lastUserId)) {
            Integer blobId = queryBuilder.getUserBlobId(lastUserId);
            if (blobId != null)
                setDialogPhotos(blobId, dialog);
        } else {
            getUserByIdUseCase.setId(lastUserId);
            getUserByIdUseCase.execute(new Subscriber<UserModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("TAG", e.getLocalizedMessage());
                }

                @Override
                public void onNext(UserModel userModel) {
                    if(userModel.getBlobId() != null)
                        setDialogPhotos(userModel.getBlobId(), dialog);
                    queryBuilder.addUserToUsersDao(userModel);
                }
            });
        }
    }

    private int getOccupantIdFromPrivateDialog(DialogModel dialogModel) {
        int currentUser = CurrentUser.getInstance().getCurrentUserId().intValue();
        Integer occupantId = null;
        List<Integer> users = dialogModel.getOccupantsIds();
        for (Integer i : users) {
            if (i != currentUser)
                occupantId = i;
        }
        return occupantId;
    }

    public void onDialogsUpdated(){
        getDialogsFromDao();
    }

    public void refreshDialogsInfo(){
        if (!BizareChatApp.getInstance().isNetworkConnected()){
            return;
        }

        unreadMessagesCountUseCase.execute(new Subscriber<Map<String, Integer>>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                getViewState().stopRefreshing();
                Log.e(TAG, e.getMessage(), e);
            }

            @Override public void onNext(Map<String, Integer> response) {
                if(response.size() > 1){
                    response.remove("total");
                    StringBuilder builder = new StringBuilder();
                    for(String dialogId : response.keySet()){
                        builder.append(dialogId).append(",");
                    }
                    builder.deleteCharAt(builder.length()-1);
                    getDialogInfo(builder.toString());
                } else {
                    getViewState().stopRefreshing();
                }
            }
        });
    }

    private void getDialogInfo(String dialogsIds){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("_id[in]", dialogsIds);
        allDialogsUseCase.setParameters(parameters);
        allDialogsUseCase.execute(new Subscriber<AllDialogsResponse>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                getViewState().stopRefreshing();
                Log.e(TAG, e.getMessage(), e);
            }

            @Override public void onNext(AllDialogsResponse response) {
                DialogModelDao modelDao = daoSession.getDialogModelDao();
                modelDao.insertOrReplaceInTx(response.getDialogModels());
                for(DialogModel dialog : response.getDialogModels()){
                    boolean replaced = false;
                    for(int i = 0; i < dialogs.size(); i++){
                        if(dialog.getDialogId().equals(dialogs.get(i).getDialogId())){
                            dialogs.set(i, dialog);
                            adapter.notifyItemChanged(i);
                            replaced = true;
                            break;
                        }
                    }
                    if(!replaced){
                        dialogs.add(dialog);
                        Collections.sort(dialogs, new ComparatorDefault());
                        adapter.notifyItemInserted(dialogs.indexOf(dialog));
                        getAndAddPhoto(dialog);
                    }
                }
                getViewState().stopRefreshing();
            }
        });
    }

    public static class ComparatorDefault implements Comparator<DialogModel> {
        @Override
        public int compare(DialogModel model1, DialogModel model2) {
            return (int) (model2.getLastMessageDateSent() - model1.getLastMessageDateSent());
        }
    }
}
