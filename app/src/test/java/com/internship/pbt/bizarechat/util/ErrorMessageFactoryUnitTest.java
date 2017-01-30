package com.internship.pbt.bizarechat.util;


import android.content.Context;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorMessageFactoryUnitTest {
    private String wrongLoginOrPassword;
    private String sessionExpired;
    private String userExists;
    private String serverCommonError;
    private String serverIsBusy;
    private String messageNoConnection;

    @Mock
    private Context context;

    @Before
    public void setUp(){
        wrongLoginOrPassword = "wrongLoginOrPassword";
        sessionExpired = "sessionExpired";
        userExists = "userExists";
        serverCommonError = "serverCommonError";
        serverIsBusy = "serverIsBusy";
        messageNoConnection = "messageNoConnection";

        when(context.getString(R.string.invalid_login_or_password)).thenReturn(wrongLoginOrPassword);
        when(context.getString(R.string.message_session_expired)).thenReturn(sessionExpired);
        when(context.getString(R.string.user_already_exists)).thenReturn(userExists);
        when(context.getString(R.string.server_common_error)).thenReturn(serverCommonError);
        when(context.getString(R.string.server_is_busy)).thenReturn(serverIsBusy);
        when(context.getString(R.string.message_no_connection)).thenReturn(messageNoConnection);
    }

    @Test
    public void checkLoginErrorMessages(){
        String message = ErrorMessageFactory.createMessageOnLogin(
                context,
                new IllegalStateException());
        Assert.assertEquals(message, wrongLoginOrPassword);

        message = ErrorMessageFactory.createMessageOnLogin(
                context,
                new HttpException(Response.error(401, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, sessionExpired);

        message = ErrorMessageFactory.createMessageOnLogin(
                context,
                new HttpException(Response.error(500, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, serverCommonError);

        message = ErrorMessageFactory.createMessageOnLogin(
                context,
                new HttpException(Response.error(503, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, serverIsBusy);

        message = ErrorMessageFactory.createMessageOnLogin(context, new IOException());
        Assert.assertEquals(message, messageNoConnection);
    }

    @Test
    public void checkRegistrationErrorMessages(){
        String message = ErrorMessageFactory.createMessageOnRegistration(
                context,
                new HttpException(Response.error(422, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, userExists);

        message = ErrorMessageFactory.createMessageOnRegistration(
                context,
                new HttpException(Response.error(401, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, sessionExpired);

        message = ErrorMessageFactory.createMessageOnRegistration(
                context,
                new HttpException(Response.error(500, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, serverCommonError);

        message = ErrorMessageFactory.createMessageOnRegistration(
                context,
                new HttpException(Response.error(503, ResponseBody.create(null, new byte[0]))));
        Assert.assertEquals(message, serverIsBusy);

        message = ErrorMessageFactory.createMessageOnRegistration(context, new IOException());
        Assert.assertEquals(message, messageNoConnection);
    }
}
