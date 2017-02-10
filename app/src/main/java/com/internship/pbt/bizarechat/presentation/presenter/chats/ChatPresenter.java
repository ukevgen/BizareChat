package com.internship.pbt.bizarechat.presentation.presenter.chats;


import com.internship.pbt.bizarechat.presentation.presenter.Presenter;

public interface ChatPresenter extends Presenter{

    void setChatImage();

    void disabledUserList();

    void sendMessage();

    void deleteMessage();

    void checkMassageLength(String string);

    void showConnectionProblem();

    void removeUserFromPrivateChat();


}
