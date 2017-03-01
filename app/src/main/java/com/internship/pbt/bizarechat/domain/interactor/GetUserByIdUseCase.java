package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import rx.Observable;

public class GetUserByIdUseCase extends UseCase<UserModel> {
    private UserRepository userRepository;
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<UserModel> buildUseCaseObservable() {
        return userRepository.getUserById(id);
    }
}
