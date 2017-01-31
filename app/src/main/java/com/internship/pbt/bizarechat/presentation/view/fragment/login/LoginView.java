package com.internship.pbt.bizarechat.presentation.view.fragment.login;


import android.content.Context;

import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

public interface LoginView extends LoadDataView{

    void showForgotPassword();

    void setPresenter(LoginPresenter presenter);

    void NavigateToMainActivity();

    Context getContextActivity();

    void setButtonSignInEnabled(boolean enabled);

    void navigateToRegistration();

    void showErrorOnPasswordRecovery();

    void showSuccessOnPasswordRecovery();

    void showCheckBoxModalDialog();
}
