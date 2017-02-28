package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.db.QueryBuilder;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUserByIdUseCase;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView>
        implements DialogsRecyclerViewAdapter.OnDialogDeleteCallback, DialogsPresenter {
    private static final int PRIVATE_DIALOG = 3;
    private DaoSession daoSession;
    private QueryBuilder queryBuilder;
    private DialogsRecyclerViewAdapter adapter;
    private DeleteDialogUseCase deleteDialogUseCase;
    private GetPhotoUseCase photoUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private CacheSharedPreferences cache;
    private int dialogsType;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;
    private Bitmap dialogBitmap;
    private Integer currentUserPhotoBlobId;


    public DialogsPresenterImp(DeleteDialogUseCase deleteDialogUseCase,
                               GetPhotoUseCase photoUseCase,
                               GetUserByIdUseCase getUserByIdUseCase,
                               DaoSession daoSession,
                               CacheSharedPreferences cache,
                               int dialogsType) {
        this.deleteDialogUseCase = deleteDialogUseCase;
        this.photoUseCase = photoUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.cache = cache;
        this.daoSession = daoSession;
        this.dialogsType = dialogsType;
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
        getViewState().showDialogs();
    }


    private List<DialogModel> getDialogsFromDao() {
        if (daoSession.getDialogModelDao().count() != 0) {
            List<DialogModel> buffer;
            if (dialogsType == PRIVATE_DIALOG) {
                buffer = queryBuilder.getPrivateDialogs(PRIVATE_DIALOG);
            } else {
                buffer = queryBuilder.getPublicDialogs(dialogsType);
            }
            dialogs.addAll(buffer);
            for (DialogModel m : buffer) {
                // getAndAddPhoto(m);
                //   dialogPhotos.put(m.getDialogId(), dialogBitmap);
            }
            adapter.setDialogPhotos(dialogPhotos);
        }
        adapter.notifyDataSetChanged();
        return dialogs;
    }

    public DialogsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onDialogDelete(int position) {
        DialogModel model = adapter.getDialogs().get(position);
        String dialogId = adapter.getDialogs().get(position).getDialogId();
        queryBuilder.removeDialog(model);

        deleteDialogUseCase.setDialogId(dialogId);
        deleteDialogUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", e.getLocalizedMessage());
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }


    private void getAndAddPhoto(DialogModel dialogModel) {
        if (dialogModel.getType() == PRIVATE_DIALOG) {
            //TODO use occupantsId to find other user
            setUserPhotoId(getOccupantIdFromPrivateDialog(dialogModel));
        } else {
            if (dialogModel.getPhoto() != "")
                setDialogPhotos(Integer.parseInt(dialogModel.getPhoto()));
        }

    }

    private void setDialogPhotos(int blobId) {
        dialogBitmap = null;
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
                dialogBitmap = bitmap;
                //dialogPhotos.put(dialogId, bitmap);
                //adapter.notifyItemChanged(position);
            }
        });
    }

    private void setUserPhotoId(int lastUserId) {
        //TODO check user exist in dao if not than download photo
        if (queryBuilder.isUserExist(lastUserId)) {
            setDialogPhotos(queryBuilder.getUserBlobId(lastUserId));
        } else {
            getUserByIdUseCase.setId(lastUserId);
            getUserByIdUseCase.execute(new Subscriber<UserModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(UserModel userModel) {
                    currentUserPhotoBlobId = userModel.getBlobId();
                    queryBuilder.addUserToUsersDao(userModel);

                }
            });
        }
    }

    private int getOccupantIdFromPrivateDialog(DialogModel dialogModel) {
        int currentUser = cache.getUserIntId();
        Integer occupantId = null;
        List<Integer> users = dialogModel.getOccupantsIds();
        for (Integer i : users) {
            if (i != currentUser)
                occupantId = i;
        }
        return occupantId;

    }

}
