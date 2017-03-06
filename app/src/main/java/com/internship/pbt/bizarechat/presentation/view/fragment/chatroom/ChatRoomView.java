package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;


import com.arellomobile.mvp.MvpView;

public interface ChatRoomView extends MvpView{
    void scrollToEnd();

    void showLoading();

    void hideLoading();

    void showEditChat();

    void showNotAdminError();
}
