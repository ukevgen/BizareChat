package com.internship.pbt.bizarechat;


import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.login.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterUnitTest {
    private LoginPresenter loginPresenter;

    @Mock
    GetTokenUseCase getTokenUseCase;

    @Mock
    LoginFragment loginView;

    @Before
    public void prepareData(){
        loginPresenter = new LoginPresenterImpl(getTokenUseCase);
        loginPresenter.setLoginView(loginView);
    }

    @Test
    public void checkFieldsAndSetButtonStateEnabled(){
        loginPresenter.checkFieldsAndSetButtonState("1234", "1234");
        verify(loginView, times(1)).setButtonSignInEnabled(true);
    }

    @Test
    public void checkFieldsAndSetButtonStateDisabled(){
        loginPresenter.checkFieldsAndSetButtonState("", "");
        verify(loginView, times(1)).setButtonSignInEnabled(false);

        loginPresenter.checkFieldsAndSetButtonState("1234", "");
        verify(loginView, times(2)).setButtonSignInEnabled(false);

        loginPresenter.checkFieldsAndSetButtonState("", "1234");
        verify(loginView, times(3)).setButtonSignInEnabled(false);
    }
}
