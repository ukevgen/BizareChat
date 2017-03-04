package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import java.util.List;

import rx.Observable;

public class GetUsersByIdsUseCase extends UseCase<List<UserModel>> {
    private UserRepository userRepository;
    private List<Integer> ids;

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public GetUsersByIdsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<List<UserModel>> buildUseCaseObservable() {
        return userRepository.getUsersByIds(ids);
    }
}
