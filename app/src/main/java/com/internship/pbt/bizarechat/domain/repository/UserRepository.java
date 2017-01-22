package com.internship.pbt.bizarechat.domain.repository;

import retrofit2.Response;
import rx.Observable;

public interface UserRepository {
    Observable<Response<Void>> resetUserPassword(String email);
}
