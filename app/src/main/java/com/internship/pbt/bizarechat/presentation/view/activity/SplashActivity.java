package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;


public class SplashActivity extends BasicActivity {
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mNavigator.navigateToLoginActivity(this);
        finish();
    }
}
