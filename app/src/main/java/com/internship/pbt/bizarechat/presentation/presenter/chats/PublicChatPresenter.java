package com.internship.pbt.bizarechat.presentation.presenter.chats;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatView;

@InjectViewState
public class PublicChatPresenter extends MvpPresenter<PublicChatView> implements ChatPresenter {

    private DaoSession daoSession;
    private DialogsRecyclerViewAdapter adapter;

    public PublicChatPresenter(DaoSession daoSession) {
        this.daoSession = daoSession;
        adapter = new DialogsRecyclerViewAdapter();
    }


    @Override
    public void setChatImage() {

    }

    @Override
    public void disabledUserList() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void deleteMessage() {

    }

    @Override
    public void checkMassageLength(String string) {

    }

    @Override
    public void showConnectionProblem() {

    }

    @Override
    public void removeUserFromPrivateChat() {

    }
}
