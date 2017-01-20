package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class RegistrationActivity extends BaseActivity implements RegistrationFragment.OnRegisterSuccess {

    public static Intent getCallingIntent(Context context){
        return new Intent(context, RegistrationActivity.class);
    }
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);

        if(savedInstanceState == null)
            addFragment(R.id.activity_layout_fragment_container, new RegistrationFragment());
    }


    @Override public void onRegisterSuccess() {
        mNavigator.navigateToMainActivity(this);
    }
}
