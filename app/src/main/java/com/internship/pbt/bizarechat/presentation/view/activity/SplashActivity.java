package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.splash.SplashPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.splash.SplashScreenPresenter;

public class SplashActivity extends BaseActivity {

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this,
                BizareChatApp.getInstance().getSessionService());

        presenter.checkIsAuthorized();
    }

    public void navigateToMainActivity() {
        new Handler().postDelayed(
                () -> {
                    mNavigator.navigateToMainActivity(this);
                    finish();
                },
                BuildConfig.SPLASH_DELAY);
    }

    public void navigateToLoginActivity() {
        new Handler().postDelayed(
                () -> {
                    mNavigator.navigateToLoginActivity(this);
                    finish();
                },
                BuildConfig.SPLASH_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}