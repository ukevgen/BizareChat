package com.internship.pbt.bizarechat.presentation.presenter.login;

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
    private Validator validator;
    private UseCase loginUseCase;
    private SessionRepository sessionRepository;
    private boolean isEmailFieldEmpty = true;
    private boolean isPasswordFieldEmpty = true;
    private CurrentUser currentUser;

    public LoginPresenterImpl(ResetPasswordUseCase resetPasswordUseCase,
                              SessionRepository sessionRepository,
                              Validator validator,
                              CurrentUser currentUser) {
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.sessionRepository = sessionRepository;
        this.validator = validator;
        this.currentUser = currentUser;
    }

    @Override
    public void requestLogin(String email, String password) {
        loginUseCase(email, password);
    }

    @Override
    public void checkIsEmailValid(String email) {
        if (validator.isValidEmail(email)) {
            if (loginView != null) loginView.showLoading();

            resetPasswordUseCase.setEmail(email);
            resetPasswordUseCase.execute(new Subscriber<Response<Void>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (loginView != null) {
                        String message = ErrorMessageFactory.
                                createMessageOnLogin(loginView.getContextActivity(), e);
                        loginView.hideLoading();
                        loginView.showError(message);
                    }
                }

                @Override
                public void onNext(Response<Void> o) {
                    if (loginView != null) {
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
        if (isEmailFieldEmpty || isPasswordFieldEmpty)
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    @Override
    public void onPasswordChanged(String password) {
        isPasswordFieldEmpty = password.isEmpty();
        if (isEmailFieldEmpty || isPasswordFieldEmpty)
            loginView.setButtonSignInEnabled(false);
        else
            loginView.setButtonSignInEnabled(true);
    }

    private void loginUseCase(String email, String password) {
        if (loginView != null) loginView.showLoading();
        this.loginUseCase = new LoginUserUseCase(sessionRepository,
                new UserRequestModel(email, password));


        this.loginUseCase.execute(new Subscriber<UserLoginResponse>() {
            @Override
            public void onCompleted() {
                if (loginView != null) loginView.hideLoading();
                navigateToMainActivity();
            }

            @Override
            public void onError(Throwable e) {
                if (loginView != null) {
                    String message = ErrorMessageFactory.
                            createMessageOnLogin(loginView.getContextActivity(), e);
                    loginView.hideLoading();
                    loginView.showError(message);
                }
            }

            @Override
            public void onNext(UserLoginResponse userLoginResponse) {
                if (currentUser.getKeepMeSignIn())
                    currentUser.setAuthorized(true);
                else
                    currentUser.setAuthorized(false);

                currentUser.setCurrentEmail(email);
                currentUser.setUserLogin(userLoginResponse.getFullName());
                currentUser.setCurrentPasswrod(password);
                currentUser.setFullName(userLoginResponse.getFullName());
                currentUser.setCurrentUserId(userLoginResponse.getId());
                currentUser.setWebsite(userLoginResponse.getWebsite());
                currentUser.setPhone(userLoginResponse.getPhone());
                if(userLoginResponse.getBlobId() != null)
                    currentUser.setAvatarBlobId(Long.valueOf(userLoginResponse.getBlobId()));
            }
        });
    }

    @Override
    public void onKeepMeSignInFalse() {
        loginView.showCheckBoxModalDialog();
        currentUser.setKeepMeSignIn(false);
    }

    @Override
    public void onKeepMeSignInTrue() {
        currentUser.setKeepMeSignIn(true);
    }

    @Override
    public void resume() {
        currentUser.clearCurrentUser();
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
