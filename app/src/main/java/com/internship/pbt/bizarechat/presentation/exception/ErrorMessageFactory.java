package com.internship.pbt.bizarechat.presentation.exception;


import android.content.Context;

import com.internship.pbt.bizarechat.R;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorMessageFactory {
    private ErrorMessageFactory(){}

    public static String createMessageOnRegistration(Context context, Throwable throwable){
        String message = "Unknown Error";
        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException)throwable;
            int code = httpException.code();
            switch (code){
                case 400:
                    message = context.getString(R.string.malformed_request_parameters);
                    break;
                case 401:
                    message = context.getString(R.string.message_session_expired);
                    break;
                case 422:
                    message = context.getString(R.string.user_already_exists);
                    break;
                case 500:
                    message = context.getString(R.string.server_common_error);
                    break;
                case 503:
                    message = context.getString(R.string.server_is_busy);
                    break;
                default:
                    message = "UNKNOWN. CODE " + code;
                    break;
            }
        } else if(throwable instanceof IOException){
            message = context.getString(R.string.message_no_connection);
        }

        return message;
    }

    public static String createMessageOnLogin(Context context, Throwable throwable){
        String message = "Unknown Error";
        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException)throwable;
            int code = httpException.code();
            switch (code){
                case 400:
                    message = context.getString(R.string.malformed_request_parameters);
                    break;
                case 401:
                    message = context.getString(R.string.message_session_expired);
                    break;
                case 500:
                    message = context.getString(R.string.server_common_error);
                    break;
                case 503:
                    message = context.getString(R.string.server_is_busy);
                    break;
                default:
                    break;
            }
        } else if(throwable instanceof IOException){
            message = context.getString(R.string.message_no_connection);
        } else if(throwable instanceof IllegalStateException){
            message = context.getString(R.string.invalid_login_or_password);
        }

        return message;
    }
}
