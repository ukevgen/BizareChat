package com.internship.pbt.bizarechat.logs;

import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Logger {

    private static final String TAG = "Logger";

    public static void logExceptionToFabric(Throwable ex) {
        logExceptionToFabric(ex, TAG);
    }

    public static void logExceptionToFabric(Throwable ex, String tag) {

        if (ex == null) {
            return;
        }

        try {
            ex.printStackTrace();
        }catch (StackOverflowError ignored) {
            Crashlytics.log("StackOverflowError in Logger " + ignored);
            return;
        }

        if(Fabric.isInitialized() && !TextUtils.isEmpty(ex.getMessage())) {
            Crashlytics.logException(ex);
        }
    }
}
