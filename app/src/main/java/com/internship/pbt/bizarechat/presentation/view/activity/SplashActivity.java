package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
           Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        /*Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent); TODO clean up comments before update*/
        finish();
    }
}
