package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.MainFragment;

public class MainActivity extends BasicActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base_layout);
        addFragment(R.id.activityLayoutFragmentContainer, new MainFragment());
    }
}