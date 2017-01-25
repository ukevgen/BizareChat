package com.internship.pbt.bizarechat.presentation.presenter.login;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.requests.User;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.interactor.LoginUserUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import retrofit2.Response;
import rx.Subscriber;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private UseCase getTokenUseCase;
    private ResetPasswordUseCase resetPasswordUseCase;
    private UseCase loginUseCase;
    private Validator validator = new Validator();

    public LoginPresenterImpl(ResetPasswordUseCase resetPasswordUseCase) {
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @Override
    public void requestSession(String email, String password) {
        Log.d("321", "requestSession()");
   /*   getTokenUseCase = new GetTokenWithAuthUseCase(new SessionDataRepository(), new User(email, password));
        getTokenUseCase.execute(new Subscriber<Session>() {
            @Override
            public void onCompleted() {
                Log.d("321", "requestSession() OnCompleted()");
                requestLogin(email, password);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("321", "requestSession() OnError()");

                String message = ErrorMessageFactory.
                        createMessageOnLogin(loginView.getContextActivity(), e);
                loginView.showError(message);
            }

            @Override
            public void onNext(Session session) {
                Log.d("321", "requestSession() OnNext. Session= " + session.getId() + " Token " + session.getToken());
                UserToken.getInstance().saveToken(session.getToken());
            }
        });*/
        requestLogin(email, password);

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
        this.loginUseCase = new LoginUserUseCase(new SessionDataRepository(),
                new User(email, password));

        Log.d("321", "request Login. TOKEN = " + UserToken.getInstance().getToken());

        loginUseCase.execute(new Subscriber<UserLoginResponce>() {
            @Override
            public void onCompleted() {
                Log.d("321", "request Login OnCompleted()");

            }

            @Override
            public void onError(Throwable e) {
                String message = ErrorMessageFactory.
                        createMessageOnLogin(loginView.getContextActivity(), e);
                loginView.showError(message);
                Log.d("321", "request Login OnError() + " + e.toString());

            }

            @Override
            public void onNext(UserLoginResponce userLoginResponce) {
                Log.d("321", "Logged with inf " + userLoginResponce.getId() + " " + userLoginResponce.getFullName());
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
}
