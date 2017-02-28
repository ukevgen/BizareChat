package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;
import com.internship.pbt.bizarechat.db.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView>
        implements DialogsRecyclerViewAdapter.OnDialogDeleteCallback, DialogsPresenter {
    private static final int THREE = 3;
    private DaoSession daoSession;
    private QueryBuilder queryBuilder;
    private DialogsRecyclerViewAdapter adapter;
    private DeleteDialogUseCase deleteDialogUseCase;
    private ContentDataRepository dataRepository;
    private CacheUsersPhotos usersPhotos;
    private GetPhotoUseCase photoUseCase;
    private int dialogsType;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;


    public DialogsPresenterImp(DeleteDialogUseCase deleteDialogUseCase,
                               GetPhotoUseCase photoUseCase,
                               DaoSession daoSession, int dialogsType) {
        this.deleteDialogUseCase = deleteDialogUseCase;
        this.photoUseCase = photoUseCase;
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
            if (dialogsType == THREE) {
                buffer = queryBuilder.getPrivateDialogs(THREE);
            } else {
                buffer = queryBuilder.getPublicDialogs(dialogsType);
            }
            dialogs.addAll(buffer);
            for (DialogModel d : buffer) {
                // TODO  getAndAddPhoto(d.getDialogId(),d.getPhoto()); change getPhoto to int
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


    private void getAndAddPhoto(String dialogId, Integer blobId) {
        for (DialogModel model : adapter.getDialogs()) {
            if (model.getType() == THREE) {
                //TODO Download user photo
            }
            else
            {
             Observable<Bitmap> bitmap = dataRepository.getPhoto(1);
            }
        }


        /*photoUseCase.setBlobId(blobId);
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
                dialogPhotos.put(dialogId, bitmap);
                //adapter.notifyItemChanged(position);
            }
        });*/
    }

}
