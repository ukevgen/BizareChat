package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.data.datamodel.request.UserRequestModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignInResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.SessionRequest;
import com.internship.pbt.bizarechat.data.net.requests.SessionWithAuthRequest;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface SessionService {
    @Headers({"Content-Type: application/json", "QuickBlox-REST-API-Version: 0.1.0"})
    @POST("/session.json")
    Observable<SessionModel> getSession(@Body SessionRequest body);

    @Headers ({"Content-Type: application/json", "QuickBlox-REST-API-Version: 0.1.0"})
    @POST("/session.json")
    Observable<SessionModel> getSessionWithAuth(@Body SessionWithAuthRequest body);

    Observable<SignInResponseModel> loginUser(@Header("QB-Token: ") String tokenHeader,
                                              @Body UserRequestModel requestModel);
}
