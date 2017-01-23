package com.internship.pbt.bizarechat.data.net.services;

import com.internship.pbt.bizarechat.data.datamodel.response.CreateFileBlobsResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.CreateFileBlobsRequest;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface FileManagerService {

    @POST("/blobs.json")
    @Headers({"Content-Type: application/json", "QuickBlox-REST-API-Version: 0.1.0"})
    Observable<CreateFileBlobsResponse> createFileBlobs(@Header(ApiConstants.TOKEN_HEADER_NAME) String token,
                                                        @Body CreateFileBlobsRequest request);

}
