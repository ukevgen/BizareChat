package com.internship.pbt.bizarechat.presentation.view.activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


public interface MainView extends MvpView {

    void hideEmptyScreen();

    void showEmptyScreen();

    void showLackOfFriends();

    @StateStrategyType(SingleStateStrategy.class)
    void startNewChatView();

    @StateStrategyType(SingleStateStrategy.class)
    void showInviteFriendsScreen();

    void showNavigationElements();

    void hideNavigationElements();

    @StateStrategyType(SingleStateStrategy.class)
    void navigateToLoginScreen();

    @StateStrategyType(SingleStateStrategy.class)
    void startUsersView();

    void confirmLogOut();

    void startBackPressed();

    void showDialogs();

    void showPublicDialogs();

    void showPrivateDialogs();


}
