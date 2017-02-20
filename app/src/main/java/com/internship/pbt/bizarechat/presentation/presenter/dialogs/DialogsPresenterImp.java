package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView> implements DialogsPresenter {

    private static final int THREE = 3;
    private DaoSession daoSession;
    private DialogsRecyclerViewAdapter adapter;
    private int dialogsType;
    private List<DialogModel> dialogs;

    public DialogsPresenterImp(DaoSession daoSession, int dialogsType) {
        this.daoSession = daoSession;
        this.dialogsType = dialogsType;
        adapter = new DialogsRecyclerViewAdapter();
        dialogs = new ArrayList<>();
    }

    @Override
    public void checkConnectionProblem() {

    }

    @Override
    public void deleteDialog() {

    }

    @Override
    public void openDialog() {

    }

    @Override
    public void loadDialogs() {
        adapter.setDialogs(getDialogsFromDao());
        getViewState().showDialogs();
    }

    private List<DialogModel> getDialogsFromDao() {
        if (daoSession.getDialogModelDao().count() != 0) {
            if (dialogsType != THREE) {
                dialogs = daoSession
                        .getDialogModelDao()
                        .queryBuilder()
                        .where(DialogModelDao.Properties.Type.notEq(THREE))
                        .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                        .list();
            } else {
                dialogs = daoSession
                        .getDialogModelDao()
                        .queryBuilder()
                        .where(DialogModelDao.Properties.Type.eq(dialogsType))
                        .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                        .list();
            }
        }
        return dialogs;

    }

    public DialogsRecyclerViewAdapter getAdapter() {
        return adapter;
    }


}
