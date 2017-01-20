package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class RegistrationActivity extends BaseActivity implements RegistrationFragment.OnRegisterSuccess {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        addFragment(R.id.activityLayoutFragmentContainer, new RegistrationFragment());
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, RegistrationActivity.class);
    }
    @Override public void onRegisterSuccess() {
        mNavigator.navigateToMainActivity(this);
    }
}
