package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import retrofit2.Response;
import rx.Observable;

public class UserDataRepository implements UserRepository {
    private UserService userService;

    public UserDataRepository() {
        userService = RetrofitApi.getRetrofitApi().getUserService();
    }

    public Observable<Response<Void>> resetUserPassword(String email){
//        return userService.resetUserPassword(UserToken.getInstance().getSessionToken(), email);
// TODO need to be implemented after choosing session save variant
        return null;
    }


}
