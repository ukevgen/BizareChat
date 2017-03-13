package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;

import java.io.File;

@StateStrategyType(SkipStrategy.class)
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

    void showChatRoom(DialogModel dialogModel);

    void showPublicChatRoom(DialogModel dialogModel);

    void showPrivateChatRoom(DialogModel dialogModel);

    void showNetworkError();


}