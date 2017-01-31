package com.internship.pbt.bizarechat.presentation.presenter.splash;

import com.internship.pbt.bizarechat.data.net.services.SessionService;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.SplashActivity;

import java.lang.ref.WeakReference;

public class SplashPresenter implements SplashScreenPresenter {

    private WeakReference<SplashActivity> activity;
    private SessionService sessionService;

    // TODO: 1/30/17 [Code Review] You should not use anything related to Android SDK +-
    // in your Presenter classes. Why don't you use View layer at all?
    public SplashPresenter(SplashActivity activity, SessionService sessionService) {
        this.activity = new WeakReference<>(activity);
        this.sessionService = sessionService;
    }

    @Override
    public void checkIsAuthorized() {
        if (CurrentUser.getInstance().isAuthorized() && UserToken.getInstance().isTokenExists())
            navigateToMainActivity();
        else
            navigateToLoginActivity();
    }


    @Override
    public void navigateToMainActivity() {
        if (activity != null)
            activity.get().navigateToMainActivity();
    }

    @Override
    public void navigateToLoginActivity() {
        if (activity != null)
            activity.get().navigateToLoginActivity();
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
        if (activity != null)
            activity = null;
    }
}
