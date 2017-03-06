package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

public interface NewChatView extends MvpView {
    void showUsersView();

    void hideUsersView();

    void showChatPhoto();

    void hideChatPhoto();

    void showTooLargePicture();

    void loadAvatarToImageView(File file);

    void showErrorMassage(String s);

    void getChatProperties();

    void showLoading();

    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showChatRoom();

    void showNetworkError();


}