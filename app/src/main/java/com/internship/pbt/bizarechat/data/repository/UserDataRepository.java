package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;

import retrofit2.Response;
import rx.Observable;

public class UserDataRepository implements UserRepository {
    private UserService userService;

    public UserDataRepository(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Observable<Response<Void>> resetUserPassword(String email){
        return userService.resetUserPassword(UserToken.getInstance().getToken(), email);
    }

    @Override
    public Observable<AllUsersResponse> getAllUsers(Integer page, String order){
        return userService.getAllUsers(
                UserToken.getInstance().getToken(),
                order,
                page,
                ApiConstants.USERS_PER_PAGE);
    }

    @Override
    public Observable<AllUsersResponse> getUsersByFullName(Integer page, String query){
        return userService.getUsersByFullName(
                UserToken.getInstance().getToken(),
                page,
                ApiConstants.USERS_PER_PAGE,
                query);
    }
}
