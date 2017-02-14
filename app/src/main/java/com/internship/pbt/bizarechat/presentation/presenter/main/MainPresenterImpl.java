package com.internship.pbt.bizarechat.presentation.presenter.main;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.MainView;

@InjectViewState
public class MainPresenterImpl extends MvpPresenter<MainView> implements MainPresenter {

    private void clearCurrentUserCache() {
        UserToken.getInstance().deleteToken();
        CurrentUser.getInstance().setAuthorized(false);
        CurrentUser.getInstance().clearCurrentUser();
    }

    @Override
    public void logout() {
        // TODO: implement request to Sign Out
        onLogoutSuccess();
    }

    public void navigateToUsers(){
        getViewState().startUsersView();
        getViewState().hideNavigationElements();
    }

    @Override
    public void navigateToNewChat() {
        getViewState().startNewChatView();
        getViewState().hideNavigationElements();
    }

    @Override
    public void addNewChat() {
        if (true) {
            navigateToNewChat();
        } else {
            getViewState().showLackOfFriends();
        }
    }

    @Override
    public void onLogoutSuccess() {
        getViewState().navigateToLoginScreen();
        clearCurrentUserCache();
    }

    public void confirmLogOut(){
        getViewState().confirmLogOut();
    }

    public void onBackPressed(){
        getViewState().startBackPressed();
    }
}
