package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpView;

public interface NewChatView extends MvpView{
    void showUsersView();

    void hideUsersView();

    void showChatPhoto();

    void hideChatPhoto();
}
