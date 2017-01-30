package com.internship.pbt.bizarechat.data.repository;


import android.content.Context;
import android.content.SharedPreferences;

public class UserToken {
    private volatile static UserToken INSTANCE;

    private String fileName = "session_token";
    private String tokenTag = "token";
    private SharedPreferences preferences;


    private UserToken(){
    }

    public static UserToken getInstance() {
        if (INSTANCE == null) {
            synchronized (UserToken.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserToken();
                }
            }
        }
        return INSTANCE;
    }

    public void initSharedPreferences(Context context){
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public boolean isTokenExists(){
        return getToken() != null;
    }

    public void saveToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(tokenTag, token);
        editor.apply();
    }

    public String getToken(){
        return preferences.getString(tokenTag, null);
    }

    public void deleteToken(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(tokenTag, null);
        editor.apply();
    }

}
