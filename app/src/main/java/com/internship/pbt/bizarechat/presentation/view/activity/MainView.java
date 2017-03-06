package com.internship.pbt.bizarechat.presentation.view.activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;


public interface MainView extends MvpView {

    void hideEmptyScreen();

    void showEmptyScreen();

    void showLackOfFriends();

    @StateStrategyType(SkipStrategy.class)
    void startNewChatView();

    @StateStrategyType(SkipStrategy.class)
    void showInviteFriendsScreen();

    @StateStrategyType(SkipStrategy.class)
    void showPrivateChatRoom(DialogModel dialogModel);

    @StateStrategyType(SkipStrategy.class)
    void showPublicChatRoom(DialogModel dialogModel);

    void showNavigationElements();

    void hideNavigationElements();

    @StateStrategyType(SkipStrategy.class)
    void navigateToLoginScreen();

    @StateStrategyType(SkipStrategy.class)
    void startUsersView();

    void confirmLogOut();

    @StateStrategyType(SkipStrategy.class)
    void startBackPressed();

    void showDialogs();

    @StateStrategyType(SkipStrategy.class)
    void showPublicDialogs();

    @StateStrategyType(SkipStrategy.class)
    void showPrivateDialogs();


}
