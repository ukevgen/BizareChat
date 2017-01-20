package com.internship.pbt.bizarechat.presentation.view.fragment.login;


import android.content.Context;

import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

public class LoginFragment extends BaseFragment implements LoginView{



    public interface OnLoginSuccess {
        void onLoginSuccess();
    }

    private OnLoginSuccess mOnLoginSuccess;

    @Override public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnLoginSuccess)
            mOnLoginSuccess = (OnLoginSuccess) context;

    }

    @Override public void showError(String message) {

    }

    @Override public void showEmailError() {

    }

    @Override public void showPasswordError() {

    }

    @Override public void setButtonSignInEnabled(boolean enabled) {

    }

    @Override public void showForgotPassword() {

    }

    @Override public void setPresenter(LoginPresenter presenter) {

    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void onLoginSuccess() {
        mOnLoginSuccess.onLoginSuccess();
    }
}
