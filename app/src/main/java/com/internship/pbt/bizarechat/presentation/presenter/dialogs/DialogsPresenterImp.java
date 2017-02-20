package com.internship.pbt.bizarechat.presentation.presenter.dialogs;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.DialogsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InjectViewState
public class DialogsPresenterImp extends MvpPresenter<DialogsView>
        implements DialogsRecyclerViewAdapter.OnDialogDeleteCallback{
    private static final int THREE = 3;
    private DaoSession daoSession;
    private DialogsRecyclerViewAdapter adapter;
    private int dialogsType;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;

    public DialogsPresenterImp(DaoSession daoSession, int dialogsType) {
        this.daoSession = daoSession;
        this.dialogsType = dialogsType;
        dialogs = new ArrayList<>();
        dialogPhotos = new HashMap<>();
        adapter = new DialogsRecyclerViewAdapter(dialogs, dialogPhotos);
        adapter.setOnDialogDeleteCallback(this);
    }

    public void checkConnectionProblem() {

    }

    public void deleteDialog(int position) {

    }

    public void openDialog() {

    }

    public void loadDialogs() {
        getDialogsFromDao();
        getViewState().showDialogs();
    }

    private List<DialogModel> getDialogsFromDao() {
        if (daoSession.getDialogModelDao().count() != 0) {
            List<DialogModel> buffer;
            if (dialogsType != THREE) {
                buffer = daoSession
                        .getDialogModelDao()
                        .queryBuilder()
                        .where(DialogModelDao.Properties.Type.notEq(THREE))
                        .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                        .list();
            } else {
                buffer = daoSession
                        .getDialogModelDao()
                        .queryBuilder()
                        .where(DialogModelDao.Properties.Type.eq(dialogsType))
                        .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                        .list();
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

    }
}
