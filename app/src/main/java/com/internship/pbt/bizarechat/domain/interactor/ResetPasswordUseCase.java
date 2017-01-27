package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import retrofit2.Response;
import rx.Observable;

public class ResetPasswordUseCase extends UseCase<Response<Void>>{
    private UserRepository userRepository;
    private String email;

    public ResetPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override protected Observable<Response<Void>> buildUseCaseObservable() {
        return userRepository.resetUserPassword(email);
    }

    public void setEmail(String email){
        this.email = email;
    }

}
