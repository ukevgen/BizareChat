package com.internship.pbt.bizarechat.presentation.presenter.login;


import com.internship.pbt.bizarechat.presentation.presenter.LoadDataPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

public interface LoginPresenter extends Presenter, LoadDataPresenter {

    void setLoginView(LoginView view);

    void requestLogin(String email, String password);

    void goToRegistration();

    void onForgotPasswordClicked();

    // TODO: 1/30/17 [Code Review] The methods below should be like 'onEmailChanged(String email)' and
    // 'onPasswordChanged(String password)', 'AndSetButtonState' is redundant, you should name the methods as some action,
    // there is no guarantee that its realization will contain logic with setting of button state
    void checkFieldsAndSetButtonState(String email, String password);

    void checkIsEmailValid(String email);

    void onKeepMeSignInFalse();

    void onKeepMeSignInTrue();

    void navigateToMainActivity();
}
