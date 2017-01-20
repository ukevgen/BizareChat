package com.internship.pbt.bizarechat.presentation.navigation;

import android.content.Context;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.presentation.view.activity.RegistrationActivity;

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
        if(context != null)
            context.startActivity(LoginActivity.getCollingIntent(context));
    }

    public void navigateToMainActivity(Context context){
        if(context != null)
            context.startActivity(MainActivity.getCallingIntent(context));
    }

    public void navigateToRegistrationActivity(Context context){
        if(context != null)
            context.startActivity(RegistrationActivity.getCallingIntent(context));
    }
}
