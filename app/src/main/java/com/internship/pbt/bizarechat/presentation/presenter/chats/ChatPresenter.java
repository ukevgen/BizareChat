package com.internship.pbt.bizarechat.presentation.presenter.chats;


public interface ChatPresenter{

    void setChatImage();

    void disabledUserList();

    void sendMessage();

    void deleteMessage();

    void checkMassageLength(String string);

    void showConnectionProblem();

    void removeUserFromPrivateChat();


}
