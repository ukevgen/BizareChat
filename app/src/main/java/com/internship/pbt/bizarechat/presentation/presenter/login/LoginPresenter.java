package com.internship.pbt.bizarechat.presentation.presenter.login;


import android.text.Editable;

import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.login.LoginView;

public interface LoginPresenter extends Presenter {
    void requestSession();

    void setLoginView(LoginView view);

    void requestLogin(String email, String password);

    void goToRegistration();

    void onPasswordForgot();

    void checkFieldsAndSetButtonState(Editable email, Editable password);
}
