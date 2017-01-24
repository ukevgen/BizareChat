package com.internship.pbt.bizarechat.presentation;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.internship.pbt.bizarechat.BuildConfig;

import io.fabric.sdk.android.Fabric;

public class BizareChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.CRASH_REPORTS)
            Fabric.with(this, new Crashlytics());

//        if(LeakCanary.isInAnalyzerProcess(this))
//            return;
//        LeakCanary.install(this);

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);

    }


}
