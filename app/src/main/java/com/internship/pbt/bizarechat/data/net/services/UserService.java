package com.internship.pbt.bizarechat.data.net.services;


import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {

    @GET("/users/password/reset.json")
    @Headers("QuickBlox-REST-API-Version: 0.1.0")
    Observable<Response<Void>> resetUserPassword(@Header("QB-Token") String token, @Query("email") String email);
}
