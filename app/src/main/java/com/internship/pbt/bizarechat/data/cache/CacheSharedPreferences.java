package com.internship.pbt.bizarechat.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

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

    public String getToken(){
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_TOKEN, "NoN");
    }

    public void putAccountAvatarBlobId(String value){
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_AVATAR, value);
        mEditor.apply();
    }

    public String getAccountAvatarBlobId(){
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_AVATAR, "NoN");
    }

    public void putIsUserAuthorized(boolean status){
        mEditor.putBoolean(CacheConstants.CURRENT_ACCOUNT_AUTHORIZATION, status);
    }

    public boolean isAuthorized(){
        return mSharedPreferences.getBoolean(CacheConstants.CURRENT_ACCOUNT_AUTHORIZATION, false);
    }


}
