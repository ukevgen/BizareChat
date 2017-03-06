package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ChatRoomView extends MvpView{
    void scrollToEnd();

    void showLoading();

    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showEditChat();

    void showNotAdminError();
}
