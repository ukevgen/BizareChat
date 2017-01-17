package com.internship.pbt.bizarechat.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.internship.pbt.bizarechat.presentation.navigation.Navigator;

public class BasicActivity extends Activity {

    protected Navigator mNavigator;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();

        if (mNavigator == null)
            mNavigator = Navigator.getInstance();
    }
}
