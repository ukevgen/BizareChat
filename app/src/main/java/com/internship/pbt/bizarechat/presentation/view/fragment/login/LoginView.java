package com.internship.pbt.bizarechat.presentation.view.fragment.login;


import android.content.Context;

import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

public interface LoginView extends LoadDataView{

    void showForgotPassword();

    void setPresenter(LoginPresenter presenter);

    // TODO: 1/30/17 [Code Review] Why do you need this? You should create a concrete View's action, like
    // 'showLoginSuccessMessage' or some navigation stuff, also concrete. Method of defining of the method to be used
    // should be in Presenter layer
    void onLoginSuccess();

    Context getContextActivity();

    void setButtonSignInEnabled(boolean enabled);

    void navigateToRegistration();

    void showErrorOnPasswordRecovery();

    void showSuccessOnPasswordRecovery();

    void showCheckBoxModalDialog();
}
