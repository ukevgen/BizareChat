package com.internship.pbt.bizarechat.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences.RxSharedPreferences;

import rx.Observable;

public class CacheSharedPreferences {

    private static CacheSharedPreferences sInstance;

    private SharedPreferences mSharedPreferences;
    private RxSharedPreferences mRxSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CacheSharedPreferences(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();

        mRxSharedPreferences = RxSharedPreferences.create(mSharedPreferences);
    }

    public void putToken(String value){
        mEditor.putString(CacheConstants.CURRENT_ACCOUT_TOKEN, value);
        mEditor.apply();
    }

    Observable<String> getToken(){
        return Observable.just(mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUT_TOKEN, "NoN"));
    }

}
