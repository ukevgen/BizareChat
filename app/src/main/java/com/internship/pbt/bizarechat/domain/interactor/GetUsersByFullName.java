package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import rx.Observable;


public class GetUsersByFullName extends UseCase<AllUsersResponse> {
    private UserRepository userRepository;
    private Integer page;
    private String query;

    public GetUsersByFullName(UserRepository userRepository){
        this.userRepository = userRepository;
        page = 1;
    }

    @Override
    protected Observable<AllUsersResponse> buildUseCaseObservable() {
        return userRepository.getUsersByFullName(page, query);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
