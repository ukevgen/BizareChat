package com.internship.pbt.bizarechat.presentation.presenter.login;


import com.internship.pbt.bizarechat.presentation.presenter.LoadDataPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

public interface LoginPresenter extends Presenter, LoadDataPresenter {

    void setLoginView(LoginView view);

    void requestLogin(String email, String password);

    void goToRegistration();

    void onForgotPasswordClicked();

    void onEmailChanged(String email);

    void onPasswordChanged(String password);

    void checkIsEmailValid(String email);

    void onKeepMeSignInFalse();

    void onKeepMeSignInTrue();

    void navigateToMainActivity();
}
