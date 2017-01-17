package com.internship.pbt.bizarechat.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;

public class Navigator {

    private static Navigator mInstance;

    private Navigator() {
        super();
    }

    public static Navigator getInstance() {
        if (mInstance == null)
            mInstance = new Navigator();

        return mInstance;
    }

    //TODO Navigate method`s

    public void navigateToLoginActivity(Context context) {
        Intent intentToLaunch = LoginActivity.getCollingIntent(context);
        context.startActivity(intentToLaunch);
    }
}
