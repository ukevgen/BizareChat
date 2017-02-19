package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface DialogsService {
    @GET("/Dialog.json")
    Observable<AllDialogsResponse> getDialogs(@Header(ApiConstants.TOKEN_HEADER_NAME) String tokenHeader,
                                              @QueryMap Map<String, String> parameters);
}
