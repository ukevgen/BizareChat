package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.domain.repository.SignOutRepository;

import retrofit2.Response;
import rx.Observable;


public class SignOutUseCase extends UseCase<Response<Void>> {
    SignOutRepository signOutRepository;

    public SignOutUseCase(SignOutRepository signOutRepository) {
        this.signOutRepository = signOutRepository;
    }

    @Override
    protected Observable<retrofit2.Response<Void>> buildUseCaseObservable() {
        return signOutRepository.signOutUser();
    }
}
