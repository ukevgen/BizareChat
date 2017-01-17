package com.internship.pbt.bizarechat.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;

public class Navigator {

    private static Navigator mInstance;

    public static Navigator getInstance(){

        if (mInstance == null)
            mInstance = new Navigator();

        return mInstance;
    }
    private Navigator() {
        super();
    }

    //TODO Navigate method`s

    public void navigateToLoginActivity(Context context){
        Intent intentToLaunch = LoginActivity.getCollingIntent(context);
        context.startActivity(intentToLaunch);
    }
}
