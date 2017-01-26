package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpRequestM;
import com.internship.pbt.bizarechat.domain.model.signup.ResponseSignUpModel;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import rx.Observable;

/**
 * Created by ukevgen on 24.01.2017.
 */

public class SignUpUseCase extends UseCase {

    private SessionRepository sessionRepository;
    private SignUpRequestM signUpRequestM;


    public SignUpUseCase(SessionRepository sessionRepository, SignUpRequestM signUpRequestM) {
        this.sessionRepository = sessionRepository;
        this.signUpRequestM = signUpRequestM;
    }

    @Override
    protected Observable<ResponseSignUpModel> buildUseCaseObservable() {
        return sessionRepository.signUpUser(signUpRequestM);
    }
}
