package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.BasicActivity;
import com.internship.pbt.bizarechat.presentation.view.fragment.main.MainFragment;

public class MainActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base_layout);
        addFragment(R.id.activityLayoutFragmentContainer, new MainFragment());
    }
}