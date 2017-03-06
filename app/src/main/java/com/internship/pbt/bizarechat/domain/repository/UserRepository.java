package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

public interface UserRepository {
    Observable<Response<Void>> resetUserPassword(String email);

    Observable<AllUsersResponse> getAllUsers(Integer page, String order);

    Observable<AllUsersResponse> getUsersByFullName(Integer page, String query);

    Observable<UserModel> getUserById(Integer id);

    Observable<List<UserModel>> getUsersByIds(List<Integer> ids);
}