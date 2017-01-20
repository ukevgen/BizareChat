package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginFragment;


public class LoginActivity extends BaseActivity{

    public static Intent getCollingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        addFragment(R.id.activityLayoutFragmentContainer, new LoginFragment());
    }

}
