package com.internship.pbt.bizarechat.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import rx.Observable;

public class CacheSharedPreferences {

    private static volatile CacheSharedPreferences INSTANCE;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String FILE_NAME = "CurrenAccount";
    private CacheSharedPreferences(Context context){
        mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static CacheSharedPreferences getInstance(Context context){
        CacheSharedPreferences local = INSTANCE;
        if(local == null) {
            synchronized (CacheSharedPreferences.class) {
                local = INSTANCE;
                if(local == null){
                    INSTANCE = local = new CacheSharedPreferences(context);
                }
            }
        }
        return local;
    }

    public void putToken(String value){
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_TOKEN, value);
        mEditor.apply();
    }

    Observable<String> getToken(){
        return Observable.just(mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_TOKEN, "NoN"));
    }

    public void putAccountAvatarBlobId(String value){
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_AVATAR, value);
        mEditor.apply();
    }

    Observable<String> getAccountAvatarBlobId(){
        return Observable.just(mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_AVATAR, "NoN"));
    }

}
