package com.internship.pbt.bizarechat.domain.interactor;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.requests.User;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class LoginUserUseCase extends UseCase {

    private SessionRepository sessionRepository;
    private User requestModel;

    public LoginUserUseCase(SessionRepository sessionRepository, User user) {
        this.sessionRepository = sessionRepository;
        this.requestModel = user;
    }

    @Override
    protected Observable<UserLoginResponce> buildUseCaseObservable() {
        Log.d("321", "buildUseCaseObservable() LoginUseCase");
        return sessionRepository.loginUser(requestModel);
    }
}
