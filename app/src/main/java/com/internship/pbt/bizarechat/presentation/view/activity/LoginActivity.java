package com.internship.pbt.bizarechat.presentation.view.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;
import com.internship.pbt.bizarechat.service.AlertReceiver;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class LoginActivity extends BaseActivity implements RegistrationFragment.OnRegisterSuccess,
        LoginFragment.OnLoginSuccess,
        EasyPermissions.PermissionCallbacks {

    private static final int RC_USE_CAMERA = 104;
    private static final int RC_STORAGE_PERMS = 103;
    private static final int RC_CONTACTS_PERMS = 105;
    private static final int LANDSCAPE = 2;
    private static final long REMIND_ME = 900000;


    public static Intent getCollingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        UserToken.getInstance().initSharedPreferences(this);

        if (savedInstanceState == null)
            addFragment(R.id.activity_layout_fragment_container, new LoginFragment());
        checkStoragePermission();
        checkCameraPermission();
        checkContactsPermission();

    }


    private void setNotification() {
        if (getResources().getConfiguration().orientation != LANDSCAPE) {
            long alertTime = SystemClock.elapsedRealtime() + REMIND_ME;
            Intent alertIntent = new Intent(this, AlertReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, alertTime,
                    PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RC_STORAGE_PERMS);
            }
        }

    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        RC_USE_CAMERA);
            }
        }
    }

    private void checkContactsPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this,
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        RC_CONTACTS_PERMS);
            }
        }
    }


    protected void onPause() {
        super.onPause();
        setNotification();
    }

    @Override
    public void onLoginSuccess() {
        mNavigator.navigateToMainActivity(this);
    }

    @Override
    public void onRegisterSuccess() {
        mNavigator.navigateToMainActivity(this);
    }
}

