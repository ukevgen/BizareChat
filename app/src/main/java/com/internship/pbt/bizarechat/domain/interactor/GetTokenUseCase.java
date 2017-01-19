package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class GetTokenUseCase extends UseCase{
    private SessionRepository sessionRepository;

    public GetTokenUseCase(SessionRepository sessionRepository,
                           ThreadExecutor threadExecutor,
                           PostExecutorThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
        this.sessionRepository = sessionRepository;
    }

    @Override protected Observable<Session> buildUseCaseObservable() {
        return sessionRepository.getSession();
    }
}
