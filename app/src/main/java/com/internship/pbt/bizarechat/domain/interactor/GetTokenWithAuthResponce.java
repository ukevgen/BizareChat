package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class GetTokenWithAuthResponce extends UseCase {

    private SessionRepository sessionRepository;
    private UserRequestModel requestModel;

    public GetTokenWithAuthResponce(SessionRepository sessionRepository,
                                    UserRequestModel requestModel) {
        this.sessionRepository = sessionRepository;
        this.requestModel = requestModel;
    }

    @Override
    protected Observable<Session> buildUseCaseObservable() {
        return sessionRepository.getSessionWithAuth(requestModel);
    }
}