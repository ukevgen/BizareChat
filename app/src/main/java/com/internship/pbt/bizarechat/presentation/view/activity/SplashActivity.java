package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.internship.pbt.bizarechat.presentation.AuthStore;


public class SplashActivity extends BaseActivity {

    private AuthStore mAuthStore;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
            mNavigator.navigateToRegistrationActivity(this);

        finish();
    }
}
