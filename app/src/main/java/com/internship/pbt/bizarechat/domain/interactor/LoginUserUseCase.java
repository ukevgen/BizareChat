package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class LoginUserUseCase extends UseCase<UserLoginResponse> {

    private SessionRepository sessionRepository;
    private UserRequestModel requestModel;
    private String token;

    public LoginUserUseCase(SessionRepository sessionRepository, UserRequestModel userRequestModel) {
        this.sessionRepository = sessionRepository;
        this.requestModel = userRequestModel;
    }

    @Override
    protected Observable<UserLoginResponse> buildUseCaseObservable() {
        return sessionRepository.loginUser(requestModel);
    }
}
