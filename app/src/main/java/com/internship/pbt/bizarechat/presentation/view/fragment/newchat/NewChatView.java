package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpView;

import java.io.File;

public interface NewChatView extends MvpView {
    void showUsersView();

    void hideUsersView();

    void showChatPhoto();

    void hideChatPhoto();

    void showTooLargePicture();

    void loadAvatarToImageView(File file);

    void showErrorMassage(String s);

    void setChatType(int type);

    void getChatProperties();

    void showLoading();

    void hideLoading();

    void showChatRoom();

    void showNetworkError();


}
