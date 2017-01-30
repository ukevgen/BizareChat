package com.internship.pbt.bizarechat.presentation;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.repository.UserToken;

import io.fabric.sdk.android.Fabric;

public class BizareChatApp extends Application {

    private static BizareChatApp INSTANCE = null;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.CRASH_REPORTS)
            Fabric.with(this, new Crashlytics());

        INSTANCE = this;

//        if(LeakCanary.isInAnalyzerProcess(this))
//            return;
//        LeakCanary.install(this);
        FacebookSdk.sdkInitialize(this);

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        UserToken.getInstance().initSharedPreferences(this);
    }

    public static BizareChatApp getInstance(){
        return INSTANCE;
    }
    public CacheSharedPreferences getCache(){
        return CacheSharedPreferences.getInstance(this);
    }
}
