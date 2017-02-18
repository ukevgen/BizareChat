package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface DialogsService {
    @GET("/Dialog.json")
    Observable<AllDialogsResponse> getAllDialogs(@Header(ApiConstants.TOKEN_HEADER_NAME) String tokenHeader);
}
