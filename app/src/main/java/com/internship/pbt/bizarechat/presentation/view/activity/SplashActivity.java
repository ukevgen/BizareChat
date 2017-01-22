package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.presentation.AuthStore;

public class SplashActivity extends BaseActivity {

    private AuthStore mAuthStore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(BuildConfig.SPLASH_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD

        mNavigator.navigateToLoginActivity(this);
=======
            mNavigator.navigateToLoginActivity(this);
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c

        finish();
    }
}
