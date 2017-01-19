package com.internship.pbt.bizarechat.presentation.view.login;


import android.content.Context;

import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.view.LoadDataView;

public interface LoginView extends LoadDataView{

    void showEmailError();

    void showPasswordError();

    void setButtonSignInEnabled(boolean enabled);

    void showForgotPassword();

    void setPresenter(LoginPresenter presenter);

    Context getContext();
}
