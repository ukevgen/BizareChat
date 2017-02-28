package com.internship.pbt.bizarechat.presentation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.DaoMaster;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.services.ContentService;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.data.net.services.SessionService;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.db.DBHelper;

import org.greenrobot.greendao.database.Database;

import io.fabric.sdk.android.Fabric;

public class BizareChatApp extends MultiDexApplication {

    private static BizareChatApp INSTANCE = null;
    private DBHelper dbHelper = new DBHelper(this);
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private Database db;

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
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        UserToken.getInstance().initSharedPreferences(this);

        //DB init
        /*DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "bizare-db");
        db = openHelper.getWritableDb();*/


    }

    public CacheUsersPhotos getCacheUsersPhotos() {
        return CacheUsersPhotos.getInstance(this);
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

    public DialogsService getDialogsService() {
        return RetrofitApi.getRetrofitApi().getDialogsService();
    }

    /*public DaoSession getDaoSession() {
        if (daoSession == null)
            daoSession = getDaoMaster().newSession();
        return daoSession;
    }*/

    public DaoSession getDaoSession() {
        return getInstance().dbHelper.getSession(false);
    }
    /*private DaoMaster getDaoMaster() {
        if (daoMaster == null)
            daoMaster = new DaoMaster(db);
        return daoMaster;
    }*/

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
