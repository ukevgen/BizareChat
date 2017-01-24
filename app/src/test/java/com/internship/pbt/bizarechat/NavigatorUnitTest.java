package com.internship.pbt.bizarechat;


import android.content.Context;
import android.content.Intent;

import com.internship.pbt.bizarechat.presentation.navigation.Navigator;
import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginActivity.class, MainActivity.class})
public class NavigatorUnitTest {
    private Navigator navigator;

    @Mock
    private Context context;

    @Before
    public void setUp(){
        navigator = Navigator.getInstance();
    }

    @Test
    public void navigateToLoginActivityTest(){
        PowerMockito.mockStatic(LoginActivity.class);
        Intent intent = new Intent(context, LoginActivity.class);
        when(LoginActivity.getCollingIntent(context)).thenReturn(intent);

        navigator.navigateToLoginActivity(context);
        verify(context).startActivity(intent);
    }

    @Test
    public void navigateToMainActivityTest(){
        PowerMockito.mockStatic(MainActivity.class);
        Intent intent = new Intent(context, MainActivity.class);
        when(MainActivity.getCallingIntent(context)).thenReturn(intent);

        navigator.navigateToMainActivity(context);
        verify(context).startActivity(intent);
    }

}
