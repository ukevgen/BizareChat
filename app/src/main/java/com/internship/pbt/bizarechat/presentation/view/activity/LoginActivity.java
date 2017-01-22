package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class LoginActivity extends BaseActivity implements RegistrationFragment.OnRegisterSuccess{

    public static Intent getCollingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(savedInstanceState == null)
            addFragment(R.id.activity_layout_fragment_container, new LoginFragment());
    }

<<<<<<< HEAD
    @Override
    public void onRegisterSuccess() {
        mNavigator.navigateToMainActivity(this);
=======
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c
    }
}
