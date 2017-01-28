package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.SplashActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpTest {

    private SplashActivity splashActivity;
    private AuthStore authStore;
    public void prepareData() {
        authStore = CurrentUser.getInstance();
    }

    @Test
    public void UserIsAuth(){
        authStore.setAuthorized(true);
        
    }
}
