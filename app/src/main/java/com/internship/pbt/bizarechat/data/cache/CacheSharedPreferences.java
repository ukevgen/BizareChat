package com.internship.pbt.bizarechat.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheSharedPreferences {

    private static volatile CacheSharedPreferences INSTANCE;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String FILE_NAME = "CurrenAccount";

    private CacheSharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static CacheSharedPreferences getInstance(Context context) {
        CacheSharedPreferences local = INSTANCE;
        if (local == null) {
            synchronized (CacheSharedPreferences.class) {
                local = INSTANCE;
                if (local == null) {
                    INSTANCE = local = new CacheSharedPreferences(context);
                }
            }
        }
        return local;
    }

    public void putToken(String value) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_TOKEN, value);
        mEditor.apply();
    }

    public String getToken() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_TOKEN, null);
    }

    public void putAccountAvatarBlobId(Long value) {
        mEditor.putLong(CacheConstants.CURRENT_ACCOUNT_AVATAR, value);
        mEditor.apply();
    }

    public void putStringAvatar(String s) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_AVATAR_STRING, s);
        mEditor.apply();
    }

    public void putUserLogin(String login) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_LOGIN, login);
        mEditor.apply();
    }

    public String getUserLogin() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_LOGIN, null);
    }

    public String getStringAvatar() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_AVATAR_STRING, null);
    }

    public Long getAccountAvatarBlobId() {
        long id = mSharedPreferences.getLong(CacheConstants.CURRENT_ACCOUNT_AVATAR, -1);
        if (id == -1)
            return null;
        else
            return id;
    }

    public void putIsUserAuthorized(boolean status) {
        mEditor.putBoolean(CacheConstants.CURRENT_ACCOUNT_AUTHORIZATION, status);
        mEditor.apply();
    }

    public boolean isAuthorized() {
        return mSharedPreferences.getBoolean(CacheConstants.CURRENT_ACCOUNT_AUTHORIZATION, false);
    }

    public void putCurrentPassword(String password) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_PASSWORD, password);
        mEditor.apply();
    }

    public String getCurrentPassword() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_PASSWORD, null);
    }

    public void putCurrentEmail(String email) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_EMAIL, email);
        mEditor.apply();
    }

    public String getCurrentEmail() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_EMAIL, null);
    }

    public void putCurrentFacebookId(Long id) {
        mEditor.putLong(CacheConstants.CURRENT_ACCOUNT_FACEBOOK_ID, id);
        mEditor.apply();
    }

    public Long getCurrentFacebookId() {
        Long id = mSharedPreferences.getLong(CacheConstants.CURRENT_ACCOUNT_FACEBOOK_ID, -1);
        if (id == -1)
            return null;
        else
            return id;
    }

    public void putUserId(Long id) {
        mEditor.putLong(CacheConstants.CURRENT_ACCOUNT_ID, id);
        mEditor.apply();
    }

    public Long getUserId() {
        long id = mSharedPreferences.getLong(CacheConstants.CURRENT_ACCOUNT_ID, -1);
        if (id == -1)
            return null;
        else
            return id;
    }



    public int getUserIntId() {
        int id = mSharedPreferences.getInt(CacheConstants.CURRENT_ACCOUNT_ID_IN_INT, -1);
        return id;
    }


    public void putUserId(Integer id) {
        mEditor.putInt(CacheConstants.CURRENT_ACCOUNT_ID_IN_INT, id);
        mEditor.apply();
    }


    public void putFullName(String fullName){
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_FULL_NAME, fullName);
        mEditor.apply();
    }

    public String getFullName(){
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_FULL_NAME, null);
    }


    public String getFirebaseToken() {
        return mSharedPreferences.getString(CacheConstants.CURRENT_ACCOUNT_FIREBASE_TOKEN, null);
    }

    public void putFirebaseToken(String token) {
        mEditor.putString(CacheConstants.CURRENT_ACCOUNT_FIREBASE_TOKEN, token);
        mEditor.apply();
    }

    public void putKeppMeSignIn(boolean keepMeSignIn) {
        mEditor.putBoolean(CacheConstants.CURRENT_ACCOUNT_KEEP_ME_SIGN_IN, keepMeSignIn);
        mEditor.apply();
    }

    public boolean getKeppMeSignIn() {
        return mSharedPreferences.getBoolean(CacheConstants.CURRENT_ACCOUNT_KEEP_ME_SIGN_IN, true);
    }

    public void deleteAllCache() {
        mEditor.clear();
        mEditor.apply();
    }

    public void putIsSubscribed(boolean status) {
        mEditor.putBoolean(CacheConstants.CURRENT_ACCOUNT_IS_SUBSCRIBED, status);
        mEditor.apply();
    }

    public boolean isSubscribed() {
        return mSharedPreferences.getBoolean(CacheConstants.CURRENT_ACCOUNT_IS_SUBSCRIBED, false);
    }

}
