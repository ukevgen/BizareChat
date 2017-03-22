package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ChatRoomView extends MvpView{
    void showMessageDay();

    void scrollToEnd();

    void showLoading();

    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showEditChat();

    void showNotAdminError();

    void showToLargeMessage();

    void showNetworkError();
}
