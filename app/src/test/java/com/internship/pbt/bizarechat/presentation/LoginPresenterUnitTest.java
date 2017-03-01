package com.internship.pbt.bizarechat.presentation;


import android.content.Context;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterUnitTest {
    private String[] invalidEmailTestData = {"plainaddress", "#@%^%#$@#$@#.com", "@domain.com",
                                            "Joe Smith <email@domain.com>", "email.domain.com",
                                            "email@domain@domain.com", "email@domain", "email@domain..com"};
    private String validEmail = "test@test.com";

    private LoginPresenter loginPresenter;

    @Mock
    private GetTokenUseCase getTokenUseCase;
    @Mock
    private ResetPasswordUseCase resetPasswordUseCase;
    @Mock
    private LoginFragment loginView;
    @Mock
    private Context context;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CurrentUser currentUser;

    @Before
    public void prepareData() {
        loginPresenter = new LoginPresenterImpl(resetPasswordUseCase,
                sessionRepository,
                new Validator(),
                currentUser);
        loginPresenter.setLoginView(loginView);
    }

    @Test
    public void checkEmailAndPasswordIsNotEmptyBehavior() {
        loginPresenter.onEmailChanged("1234");
        loginPresenter.onPasswordChanged("1234");
        verify(loginView, times(1)).setButtonSignInEnabled(true);
    }

    @Test
    public void checkEmailOrPasswordIsEmptyBehavior() {
        loginPresenter.onEmailChanged("");
        loginPresenter.onPasswordChanged("");
        verify(loginView, times(2)).setButtonSignInEnabled(false);

        loginPresenter.onEmailChanged("1234");
        loginPresenter.onPasswordChanged("");
        verify(loginView, times(4)).setButtonSignInEnabled(false);

        loginPresenter.onEmailChanged("");
        loginPresenter.onPasswordChanged("1234");
        verify(loginView, times(6)).setButtonSignInEnabled(false);
    }

    @Test
    public void navigateToRegistrationTest() {
        loginPresenter.goToRegistration();
        verify(loginView, times(1)).navigateToRegistration();
    }

    @Test
    public void showForgotPasswordDialog() {
        loginPresenter.onForgotPasswordClicked();
        verify(loginView, times(1)).showForgotPassword();
    }

    @Test
    public void showInvalidEmailOnPasswordRecovery(){
        for(String email : invalidEmailTestData) {
            loginPresenter.checkIsEmailValid(email);
            verify(loginView, atLeastOnce()).showErrorOnPasswordRecovery();
        }
    }

    @Test
    public void checkEmailIsValid(){
        loginPresenter.checkIsEmailValid(validEmail);
        verify(loginView, never()).showErrorOnPasswordRecovery();
    }

    @Test
    public void checkPasswordRecoverySuccessfullySent(){
        ArgumentCaptor<Subscriber> captor = ArgumentCaptor.forClass(Subscriber.class);
        loginPresenter.checkIsEmailValid(validEmail);
        verify(resetPasswordUseCase).execute(captor.capture());
        captor.getValue().onNext(Response.success(Void.TYPE));
        verify(loginView).showSuccessOnPasswordRecovery();
    }

    @Test
    public void checkRecoveryPasswordHandleExceptions(){
        ArgumentCaptor<Subscriber> captor = ArgumentCaptor.forClass(Subscriber.class);
        when(context.getString(R.string.message_session_expired)).thenReturn("");
        when(loginView.getContextActivity()).thenReturn(context);

        loginPresenter.checkIsEmailValid(validEmail);
        verify(resetPasswordUseCase).execute(captor.capture());
        captor.getValue().onError(new HttpException(
                Response.error(401,
                        ResponseBody.create(MediaType.parse("text/html"), "This is testException")
                )
        ));
        verify(loginView).showError("");
    }

    @Test
    public void sendButtonDisabled() {
    }

    @Test
    public void ifKeepMeSignInUncheckShowDialog(){
        loginPresenter.onKeepMeSignInFalse();
        verify(loginView).showCheckBoxModalDialog();
    }


}

