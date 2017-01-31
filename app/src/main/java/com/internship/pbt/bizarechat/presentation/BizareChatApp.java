package com.internship.pbt.bizarechat.presentation;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.services.ContentService;
import com.internship.pbt.bizarechat.data.net.services.SessionService;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.data.repository.UserToken;

import io.fabric.sdk.android.Fabric;

public class BizareChatApp extends Application {

    private static BizareChatApp INSTANCE = null;

    public static BizareChatApp getInstance() {
        return INSTANCE;
    }

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

    public CacheSharedPreferences getCache() {
        return CacheSharedPreferences.getInstance(this);
    }

    public ContentService getContentService() {
        return RetrofitApi.getRetrofitApi().getContentService();
    }

    public UserService getUserService() {
        return RetrofitApi.getRetrofitApi().getUserService();
    }

    public SessionService getSessionService() {
        return RetrofitApi.getRetrofitApi().getSessionService();
    }
}
