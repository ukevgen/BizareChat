package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.User;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class GetTokenWithAuthUseCase extends UseCase {

    private SessionRepository sessionRepository;
    private User requestModel;

    public GetTokenWithAuthUseCase(SessionRepository sessionRepository,
                                   User requestModel) {
        this.sessionRepository = sessionRepository;
        this.requestModel = requestModel;
    }

    @Override
    protected Observable<Session> buildUseCaseObservable() {
        return sessionRepository.getSessionWithAuth(requestModel);
    }
}
