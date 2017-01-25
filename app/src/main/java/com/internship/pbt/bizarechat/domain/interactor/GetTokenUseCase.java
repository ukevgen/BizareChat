package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class GetTokenUseCase extends UseCase<Session>{
    private SessionRepository sessionRepository;

    public GetTokenUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Observable<Session> buildUseCaseObservable() {
        return sessionRepository.getSession();
    }
}
