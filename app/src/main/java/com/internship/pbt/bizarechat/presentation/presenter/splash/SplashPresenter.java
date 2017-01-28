package com.internship.pbt.bizarechat.presentation.presenter.splash;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.domain.interactor.LoginUserUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.SplashActivity;

import rx.Subscriber;

public class SplashPresenter implements SplashScreenPresenter {

    private UseCase sessionRequestUseCase;
    private UseCase loginUseCase;
    private SplashActivity activity;


    public SplashPresenter(SplashActivity activity) {
        this.activity = activity;
    }

    @Override
    public void reSignIn() {
        this.sessionRequestUseCase = new GetTokenUseCase(new SessionDataRepository());
        sessionRequestUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {
                loginUseCase(CurrentUser.getInstance().getCurrentEmail(),
                        CurrentUser.getInstance().getCurrentPassword());
                Log.d("54321", "ReSignIn onComplete");

            }

            @Override
            public void onError(Throwable e) {
                onLoginFailure();
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void loginUseCase(String email, String password) {
        this.loginUseCase = new LoginUserUseCase(new SessionDataRepository(),
                new UserRequestModel(email, password));

        Log.d("54321", "request Login. TOKEN = " + UserToken.getInstance().getToken());

        this.loginUseCase.execute(new Subscriber<UserLoginResponse>() {
            @Override
            public void onCompleted() {
                Log.d("54321", "request Login OnCompleted()");
                CurrentUser.getInstance().setAuthorized(true);
                CurrentUser.getInstance().setCurrentEmail(email);
                CurrentUser.getInstance().setCurrentPasswrod(password);
                onLoginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("54321", "request Login OnError() + " + e.toString());

            }

            @Override
            public void onNext(UserLoginResponse userLoginResponse) {
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        activity.navigateToLoginActivity();
    }

    @Override
    public void onLoginFailure() {
        activity.navigateToLoginActivity();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (sessionRequestUseCase != null)
            sessionRequestUseCase.unsubscribe();
        if (loginUseCase != null)
            loginUseCase.unsubscribe();
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void hideViewLoading() {

    }
}
