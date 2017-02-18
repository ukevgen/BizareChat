package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;

import retrofit2.Response;
import rx.Observable;

public interface UserRepository {
    Observable<Response<Void>> resetUserPassword(String email);

    Observable<AllUsersResponse> getAllUsers(Integer page);

}
