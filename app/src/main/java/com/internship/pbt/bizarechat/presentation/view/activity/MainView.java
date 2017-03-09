package com.internship.pbt.bizarechat.presentation.view.activity;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showUserInfo();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideEmptyScreen();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmptyScreen();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLackOfFriends();

    void startNewChatView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAvatarImage(Bitmap image);

    void showInviteFriendsScreen();

    void showPrivateChatRoom(DialogModel dialogModel);

    void showPublicChatRoom(DialogModel dialogModel);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showNavigationElements();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void closeDrawer();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideNavigationElements();

    void navigateToLoginScreen();

    void startUsersView();

    void showSettingsScreen();

    void confirmLogOut();

    void startBackPressed();

    void showPublicDialogs();

    void showPrivateDialogs();

}
