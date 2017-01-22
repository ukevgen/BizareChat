package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import retrofit2.Response;
import rx.Observable;

public class ResetPasswordUseCase extends UseCase{
    private UserRepository userRepository;
    private String email;

    public ResetPasswordUseCase(String email,
                                UserRepository userRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutorThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
        this.email = email;
    }

    @Override protected Observable<Response<Void>> buildUseCaseObservable() {
        return userRepository.resetUserPassword(email);
    }
}
