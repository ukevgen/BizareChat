package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;
import com.internship.pbt.bizarechat.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView>
        implements DialogsRecyclerViewAdapter.OnDialogDeleteCallback, DialogsPresenter {
    private static final int THREE = 3;
    private DaoSession daoSession;
    private QueryBuilder queryBuilder;
    private DialogsRecyclerViewAdapter adapter;
    private DeleteDialogUseCase deleteDialogUseCase;
    private int dialogsType;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;


    public DialogsPresenterImp(DeleteDialogUseCase deleteDialogUseCase,
                               DaoSession daoSession, int dialogsType) {
        this.deleteDialogUseCase = deleteDialogUseCase;
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
        }
        adapter.notifyDataSetChanged();
        return dialogs;
    }

    public DialogsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onDialogDelete(int position) {
        String dialogId = adapter.getDialogs().get(position).getDialogId();
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
              //  queryBuilder.removeDialog(adapter.getDialogs().get(position));
            }
        });

    }
}
