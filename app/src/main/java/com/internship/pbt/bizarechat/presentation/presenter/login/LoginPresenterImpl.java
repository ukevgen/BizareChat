package com.internship.pbt.bizarechat.presentation.presenter.login;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.interactor.LoginUserUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import retrofit2.Response;
import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private ResetPasswordUseCase resetPasswordUseCase;
    private Validator validator = new Validator();
    private UseCase loginUseCase;
    private SessionRepository sessionRepository;
    private boolean isEmailFieldEmpty = true;
    private boolean isPasswordFieldEmpty = true;

    public LoginPresenterImpl(ResetPasswordUseCase resetPasswordUseCase,
                              SessionRepository sessionRepository) {
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void requestLogin(String email, String password) {
        loginUseCase(email, password);
    }

    @Override
    public void checkIsEmailValid(String email) {
        if (validator.isValidEmail(email)) {
            if(loginView != null) loginView.showLoading();

            resetPasswordUseCase.setEmail(email);
            resetPasswordUseCase.execute(new Subscriber<Response<Void>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if(loginView != null) {
                        String message = ErrorMessageFactory.
                                createMessageOnLogin(loginView.getContextActivity(), e);
                        loginView.hideLoading();
                        loginView.showError(message);
                    }
                }

                @Override
                public void onNext(Response<Void> o) {
                    if(loginView != null) {
                        loginView.showSuccessOnPasswordRecovery();
                        loginView.hideLoading();
                    }
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
    public void onForgotPasswordClicked() {
        loginView.showForgotPassword();
    }

    @Override
    public void onEmailChanged(String email) {
        isEmailFieldEmpty = email.isEmpty();
        if(isEmailFieldEmpty || isPasswordFieldEmpty)
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    @Override
    public void onPasswordChanged(String password) {
        isPasswordFieldEmpty = password.isEmpty();
        if(isEmailFieldEmpty || isPasswordFieldEmpty)
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    private void loginUseCase(String email, String password) {
        if(loginView != null) loginView.showLoading();
        this.loginUseCase = new LoginUserUseCase(sessionRepository,
                new UserRequestModel(email, password));

        Log.d("321", "request Login. TOKEN = " + UserToken.getInstance().getToken());

        this.loginUseCase.execute(new Subscriber<UserLoginResponse>() {
            @Override
            public void onCompleted() {
                Log.d("321", "request Login OnCompleted()");
                if(loginView != null) loginView.hideLoading();
                navigateToMainActivity();
            }

            @Override
            public void onError(Throwable e) {
                if(loginView !=null) {
                    String message = ErrorMessageFactory.
                            createMessageOnLogin(loginView.getContextActivity(), e);
                    loginView.hideLoading();
                    loginView.showError(message);
                }
                Log.d("321", "request Login OnError() + " + e.toString());
            }

            @Override
            public void onNext(UserLoginResponse userLoginResponse) {
                if (CurrentUser.getInstance().getKeepMeSignIn())
                    CurrentUser.getInstance().setAuthorized(true);

                else
                    CurrentUser.getInstance().setAuthorized(false);

                CurrentUser.getInstance().setCurrentEmail(email);
                CurrentUser.getInstance().setCurrentPasswrod(password);
            }
        });
    }

    @Override
    public void onKeepMeSignInFalse() {
        loginView.showCheckBoxModalDialog();
        CurrentUser.getInstance().setKeepMeSignIn(false);
    }

    @Override
    public void onKeepMeSignInTrue() {
        CurrentUser.getInstance().setKeepMeSignIn(true);
    }

    @Override
    public void resume() {
        CurrentUser.getInstance().clearCurrentUser();
        UserToken.getInstance().deleteToken();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        loginView = null;
    }

    @Override
    public void stop() {
        if (loginUseCase != null)
            loginUseCase.unsubscribe();
        if (resetPasswordUseCase != null)
            resetPasswordUseCase.unsubscribe();
    }

    @Override
    public void navigateToMainActivity() {
        loginView.NavigateToMainActivity();
    }
}
