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
        clearCurrentUserCache();
    }



    @Override
    public void navigateToNewChat() {

    }

    @Override
    public void addNewChat() {
        if(true) {
            getViewState().startNewChatView();
        } else{
            getViewState().showLackOfFriends();
        }
    }
}
