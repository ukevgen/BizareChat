package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import retrofit2.Response;
import rx.Observable;

public class UserDataRepository implements UserRepository {
    private UserService userService;

    public UserDataRepository(UserService userService) {
        this.userService = userService;
    }

    public Observable<Response<Void>> resetUserPassword(String email){
        return userService.resetUserPassword(UserToken.getInstance().getToken(), email);
    }

}
