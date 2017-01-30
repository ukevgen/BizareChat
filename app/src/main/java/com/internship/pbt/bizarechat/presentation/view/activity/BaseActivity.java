package com.internship.pbt.bizarechat.presentation.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.internship.pbt.bizarechat.presentation.navigation.Navigator;

public class BaseActivity extends AppCompatActivity {

    protected Navigator mNavigator = Navigator.getInstance();


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 1/30/17 [Code Review] This code is redundant, you've initialized navigator as property already
        if (mNavigator == null)
            mNavigator = Navigator.getInstance();
    }

    // TODO: 1/30/17 [Code Review] This can be removed
    @Override protected void onResume() {
        super.onResume();
    }

    protected void addFragment(int containerViewId, Fragment fragment){
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}
