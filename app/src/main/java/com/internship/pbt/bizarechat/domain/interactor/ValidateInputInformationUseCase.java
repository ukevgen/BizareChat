package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;

import rx.Observable;

public class ValidateInputInformationUseCase extends UseCase {

    Observable<ValidationInformation> mValidationInformation;

    protected ValidateInputInformationUseCase(ThreadExecutor threadExecutor,
                                              PostExecutorThread postExecutionThread,
                                              Observable<ValidationInformation> validationInformation) {
        super(threadExecutor, postExecutionThread);
        mValidationInformation = validationInformation;
    }

    @Override protected Observable<ValidationInformation> buildUseCaseObservable() {
        return mValidationInformation;
    }
}
