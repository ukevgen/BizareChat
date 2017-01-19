package com.internship.pbt.bizarechat.presentation.exception;


import android.content.Context;

import com.internship.pbt.bizarechat.R;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorMessageFactory {
    private ErrorMessageFactory(){}

    public static String createMessage(Context context, Exception exception){
        String message = "";
        if(exception instanceof HttpException){
            HttpException httpException = (HttpException)exception;
            int code = httpException.code();
            switch (code){
                case 401:
                    message = context.getString(R.string.message_session_expired);
                    break;
                default:
                    break;
            }
        } else if(exception instanceof IOException){
            message = context.getString(R.string.message_no_connection);
        }

        return message;
    }
}
