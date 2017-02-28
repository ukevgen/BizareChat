package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface UserService {

    @GET("/users/password/reset.json")
    @Headers("QuickBlox-REST-API-Version: 0.1.0")
    Observable<Response<Void>> resetUserPassword(@Header(ApiConstants.TOKEN_HEADER_NAME) String token,
                                                 @Query("email") String email);

    @GET("/users.json")
    @Headers("QuickBlox-REST-API-Version: 0.1.0")
    Observable<AllUsersResponse> getAllUsers(@Header(ApiConstants.TOKEN_HEADER_NAME) String token,
                                             @Query("order") String order,
                                             @Query("page") Integer page,
                                             @Query("per_page") Integer perPage);

    @GET("/users/by_full_name.json")
    @Headers("QuickBlox-REST-API-Version: 0.1.0")
    Observable<AllUsersResponse> getUsersByFullName(@Header(ApiConstants.TOKEN_HEADER_NAME) String token,
                                                    @Query("page") Integer page,
                                                    @Query("per_page") Integer perPage,
                                                    @Query("full_name") String query);

    @GET("/users/{id}.json")
    @Headers("QuickBlox-REST-API-Version: 0.1.0")
    Observable<UserModel> getUserById(@Header(ApiConstants.TOKEN_HEADER_NAME) String token,
                                      @Path("id") Integer id);
}
