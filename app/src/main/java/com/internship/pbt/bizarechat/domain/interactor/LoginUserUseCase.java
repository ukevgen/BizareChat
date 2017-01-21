package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.request.UserRequestModel;
import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

public class LoginUserUseCase extends UseCase {

    SessionRepository sessionRepository;
    private UserRequestModel requestModel;
    protected LoginUserUseCase(ThreadExecutor threadExecutor,
                               PostExecutorThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable<UserLoginResponce> buildUseCaseObservable() {
        return sessionRepository.loginUser(requestModel);
    }
}
