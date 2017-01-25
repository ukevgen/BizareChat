package com.internship.pbt.bizarechat.presentation.presenter.login;

import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import retrofit2.Response;
import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private GetTokenUseCase getTokenUseCase;
    private ResetPasswordUseCase resetPasswordUseCase;
    private Validator validator = new Validator();

    public LoginPresenterImpl(GetTokenUseCase getTokenUseCase,
                              ResetPasswordUseCase resetPasswordUseCase) {
        this.getTokenUseCase = getTokenUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @Override
    public void requestSession() {
        getTokenUseCase.execute(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                String message = ErrorMessageFactory.
                        createMessageOnLogin(loginView.getContextActivity(), e);
                loginView.showError(message);
            }

            @Override
            public void onNext(Session session) {
                UserToken.getInstance().saveToken(session.getToken());
            }
        });
    }

    @Override
    public void checkIsEmailValid(String email){
        if(validator.isValidEmail(email)){
            resetPasswordUseCase.setEmail(email);

            resetPasswordUseCase.execute(new Subscriber<Response<Void>>() {
                @Override public void onCompleted() {

                }

                @Override public void onError(Throwable e) {
                    String message = ErrorMessageFactory.
                            createMessageOnLogin(loginView.getContextActivity(), e);
                    loginView.showError(message);
                }

                @Override public void onNext(Response<Void> o) {
                    loginView.showSuccessOnPasswordRecovery();
                }
            });
        } else {
            loginView.showErrorOnPasswordRecovery();
        }
    }

    @Override
    public void showViewLoading() {
        loginView.showLoading();
    }

    @Override
    public void hideViewLoading() {
        loginView.hideLoading();
    }

    @Override
    public void setLoginView(LoginView view) {
        loginView = view;
    }

    @Override
    public void goToRegistration() {
        loginView.navigateToRegistration();
    }

    @Override
    public void onPasswordForgot() {
        loginView.showForgotPassword();
    }

    @Override
    public void checkFieldsAndSetButtonState(String email, String password) {
        if (email.isEmpty() || password.isEmpty())
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    @Override
    public void requestLogin(String email, String password) {
        //TODO implement login
    }

    @Override
    public void onKeepMeSignInFalse() {
        loginView.showCheckBoxModalDialog();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    @Override
    public void destroy() {
        loginView = null;
    }
}
