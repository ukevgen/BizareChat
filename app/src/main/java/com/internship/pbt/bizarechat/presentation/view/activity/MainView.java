package com.internship.pbt.bizarechat.presentation.view.activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


public interface MainView extends MvpView {

    void hideEmptyScreen();

    void showEmptyScreen();

    void showLackOfFriends();

    @StateStrategyType(SkipStrategy.class)
    void startNewChatView();

    @StateStrategyType(SkipStrategy.class)
    void showInviteFriendsScreen();

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
