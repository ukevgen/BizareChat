package com.internship.pbt.bizarechat.presentation.presenter.newchat;


import android.net.Uri;

public interface NewChatPresenter {
    void verifyAndLoadAvatar(Uri uri);

    void showTooLargeImage();

    void createNewChat();

    void createRequestForNewChat(String chatName);

    void checkConnection();
}
