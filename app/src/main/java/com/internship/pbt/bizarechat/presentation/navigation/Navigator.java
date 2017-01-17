package com.internship.pbt.bizarechat.presentation.navigation;

import android.content.Context;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;

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
        context.startActivity(LoginActivity.getCollingIntent(context));
    }

    public void navigateToMainActivity(Context context){
        context.startActivity(MainActivity.getCallingIntent(context));
    }
}
