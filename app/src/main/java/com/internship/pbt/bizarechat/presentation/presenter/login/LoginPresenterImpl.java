package com.internship.pbt.bizarechat.presentation.presenter.login;

import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private GetTokenUseCase getTokenUseCase;

    public LoginPresenterImpl(GetTokenUseCase getTokenUseCase){
        this.getTokenUseCase = getTokenUseCase;
    }

    @Override public void requestSession() {
        getTokenUseCase.execute(new Subscriber<Session>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                String message = ErrorMessageFactory.createMessage(loginView.getContext(), e);
                loginView.showError(message);
            }

            @Override public void onNext(Session session) {
                //TODO   here we should save session token
            }
        });
    }

    @Override public void showViewLoading() {

    }

    @Override public void hideViewLoading() {

    }

    @Override public void setLoginView(LoginView view) {
        loginView = view;
    }

    @Override public void goToRegistration() {
        loginView.navigateToRegistration();
    }

    @Override public void onPasswordForgot() {
        loginView.showForgotPassword();
    }

    @Override public void checkFieldsAndSetButtonState(String email, String password) {
        if(email.isEmpty() || password.isEmpty())
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    @Override public void requestLogin(String email, String password) {
        //TODO implement login request
    }

    @Override public void resume() {

    }

    @Override public void pause() {

    }


    @Override public void destroy() {
        loginView = null;
    }
}
