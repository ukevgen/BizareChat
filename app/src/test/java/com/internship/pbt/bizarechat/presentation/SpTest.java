package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.SplashActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        CurrentUser.class,
        UserToken.class
        })
public class SpTest {

    private SplashActivity splashActivity;
    private AuthStore authStore;
    public void prepareData() {
        PowerMockito.mockStatic(CurrentUser.class);
        PowerMockito.mockStatic(UserToken.class);
    }

    @Test
    public void UserIsAuth(){
        
    }
}
