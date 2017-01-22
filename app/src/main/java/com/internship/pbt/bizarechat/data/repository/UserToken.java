package com.internship.pbt.bizarechat.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserToken {
    private volatile static UserToken INSTANCE;

    private String logTag = UserToken.class.getSimpleName();
    private long tokenTimeout = 7200000;
    private String tokenTag = "session_token";
    private String expirationDateTag = "session_expiration_date";
    private SharedPreferences preferences;
    private SimpleDateFormat formatter;


    private UserToken(){
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault());
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

    public boolean isTokenExists(){
        return preferences != null && !preferences.getString(tokenTag, "").isEmpty();
    }

    public void saveToken(Context context, String token, String date){
        if(preferences == null)
            preferences = context.getSharedPreferences(tokenTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(tokenTag, token);
        long timestamp = convertStringDateToTimestamp(date);
        if(timestamp > 0)
            editor.putLong(expirationDateTag, timestamp);

        editor.apply();
    }

    public void updateExpirationDate(String date){
        SharedPreferences.Editor editor = preferences.edit();

        long timestamp = convertStringDateToTimestamp(date);
        if(timestamp > 0)
            editor.putLong(expirationDateTag, timestamp);

        editor.apply();
    }

    public String getSessionToken(){
        return preferences.getString(tokenTag, "");
    }

    public boolean isSessionExpired(){
        long timestamp = preferences.getLong(expirationDateTag, 0);
        long currentTimestamp = System.currentTimeMillis();

        return currentTimestamp - timestamp > tokenTimeout;
    }

    private long convertStringDateToTimestamp(String source){
        try {
            Date date = formatter.parse(source);
            return date.getTime();
        }catch (ParseException ex){
            Log.e(logTag, ex.getMessage(), ex);
        }

        return 0;
    }
}
