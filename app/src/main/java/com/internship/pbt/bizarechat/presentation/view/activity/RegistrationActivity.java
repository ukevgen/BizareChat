package com.internship.pbt.bizarechat.presentation.view.activity;

import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class RegistrationActivity extends BaseActivity implements RegistrationFragment.OnRegisterSuccess {

    @Override public void onRegisterSuccess() {
        mNavigator.navigateToMainActivity(this);
    }
}
