package com.internship.pbt.bizarechat.data.net.services;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface DialogsService {
    @GET("/Dialog.json")
    Observable<AllDialogsResponse> getAllDialogs(@Header(ApiConstants.TOKEN_HEADER_NAME) String tokenHeader);

    @Headers("Content-Type: application/json")
    @PUT("chat/Dialog/{dialog_id}.json")
    Observable<DialogUpdateResponseModel> updateDialog(@Header(ApiConstants.TOKEN_HEADER_NAME) String tokenHeader,
                                                       @Path("dialog_id") String dialogId,
                                                       @Body DialogUpdateRequestModel updateRequestModel);
}
