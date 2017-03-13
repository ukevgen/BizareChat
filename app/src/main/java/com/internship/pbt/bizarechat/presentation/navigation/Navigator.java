package com.internship.pbt.bizarechat.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;

public class Navigator {

    private static Navigator mInstance;

    private Navigator() {
        super();
    }

    public static Navigator getInstance() {
        if (mInstance == null) {
            mInstance = new Navigator();
        }

        return mInstance;
    }

    public void navigateToLoginActivity(Context context) {
        if (context != null) {
            context.startActivity(LoginActivity.getCollingIntent(context));
        }
    }

    public void navigateToMainActivity(Context context) {
        if (context != null) {
            if (context instanceof LoginActivity) {
                Intent intent = MainActivity.getCallingIntent(context);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                ((LoginActivity) context).finish();
                context.startActivity(intent);
            }
        }
        context.startActivity(MainActivity.getCallingIntent(context));
    }

}
