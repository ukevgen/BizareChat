package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.CreateSubscriptionRequest;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface NotificationService {
    @Headers({"Content-Type: application/json", "QuickBlox-REST-API-Version: 0.1.0"})
    @POST("/subscriptions.json")
    Observable<CreateSubscriptionResponse[]> createSubscription(@Header(ApiConstants.TOKEN_HEADER_NAME) String tokenHeader,
                                                              @Body CreateSubscriptionRequest request);
}
