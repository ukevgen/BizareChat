package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class LoginUserUseCase extends UseCase {

    private SessionRepository sessionRepository;
    private UserRequestModel requestModel;

    protected LoginUserUseCase(SessionRepository sessionRepository, UserRequestModel userRequestModel) {
        this.sessionRepository = sessionRepository;
        this.requestModel = userRequestModel;
    }

    @Override
    protected Observable<UserLoginResponce> buildUseCaseObservable() {
        return sessionRepository.loginUser(requestModel);
    }
}
