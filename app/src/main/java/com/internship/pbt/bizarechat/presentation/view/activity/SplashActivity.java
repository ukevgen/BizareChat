package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.presentation.AuthStore;

public class SplashActivity extends BaseActivity {

    private AuthStore mAuthStore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(
                () -> mNavigator.navigateToLoginActivity(this),
                BuildConfig.SPLASH_DELAY);

        finish();
    }
}
