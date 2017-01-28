package com.internship.pbt.bizarechat.presentation.presenter.login;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.interactor.LoginUserUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import retrofit2.Response;
import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private ResetPasswordUseCase resetPasswordUseCase;
    private Validator validator = new Validator();

    public LoginPresenterImpl(ResetPasswordUseCase resetPasswordUseCase) {
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @Override
    public void checkIsEmailValid(String email) {
        if (validator.isValidEmail(email)) {
            resetPasswordUseCase.setEmail(email);

            resetPasswordUseCase.execute(new Subscriber<Response<Void>>() {
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
                public void onNext(Response<Void> o) {
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
        UseCase loginUseCase = new LoginUserUseCase(new SessionDataRepository(),
                new UserRequestModel(email, password));

        Log.d("321", "request Login. TOKEN = " + UserToken.getInstance().getToken());

        loginUseCase.execute(new Subscriber<UserLoginResponse>() {
            @Override
            public void onCompleted() {
                Log.d("321", "request Login OnCompleted()");
                onLoginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                String message = ErrorMessageFactory.
                        createMessageOnLogin(loginView.getContextActivity(), e);
                loginView.showError(message);
                e.printStackTrace();
                Log.d("321", "request Login OnError() + " + e.toString());

            }

            @Override
            public void onNext(UserLoginResponse userLoginResponse) {
                Log.d("321", "Logged with inf " + userLoginResponse.getId() + " " + userLoginResponse.getFullName());
            }
        });
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

    @Override
    public void onLoginSuccess() {
        loginView.onLoginSuccess();
    }
}
