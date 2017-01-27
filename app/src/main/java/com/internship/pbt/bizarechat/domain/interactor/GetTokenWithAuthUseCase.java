package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class GetTokenWithAuthUseCase extends UseCase<Session>  {

    private SessionRepository sessionRepository;
    private UserRequestModel requestModel;

    public GetTokenWithAuthUseCase(SessionRepository sessionRepository,
                                   UserRequestModel requestModel) {
        this.sessionRepository = sessionRepository;
        this.requestModel = requestModel;
    }

    @Override
    protected Observable<Session> buildUseCaseObservable() {
        return sessionRepository.getSessionWithAuth(requestModel);
    }
}
