package com.internship.pbt.bizarechat.presentation.presenter.main;


import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

public class MainPresenterImpl implements MainPresenter {

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
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

}
