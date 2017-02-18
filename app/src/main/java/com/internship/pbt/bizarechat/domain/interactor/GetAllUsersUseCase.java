package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import rx.Observable;

public class GetAllUsersUseCase extends UseCase<AllUsersResponse> {
    private UserRepository userRepository;
    private Integer page;

    public GetAllUsersUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
        page = 1;
    }

    @Override
    protected Observable<AllUsersResponse> buildUseCaseObservable() {
        return userRepository.getAllUsers(page);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
