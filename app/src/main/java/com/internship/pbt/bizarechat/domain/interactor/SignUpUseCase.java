package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.SignUpRequestModel;
import com.internship.pbt.bizarechat.domain.model.UserSignUpResponce;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

/**
 * Created by ukevgen on 24.01.2017.
 */

public class SignUpUseCase extends UseCase {

    private SessionRepository sessionRepository;
    private SignUpRequestModel signUpRequestModel;


    public SignUpUseCase(SessionRepository sessionRepository, SignUpRequestModel signUpRequestModel) {
        this.sessionRepository = sessionRepository;
        this.signUpRequestModel = signUpRequestModel;
    }

    @Override
    protected Observable<UserSignUpResponce> buildUseCaseObservable() {
        return sessionRepository.signUpUser(signUpRequestModel);
    }
}
