package com.internship.pbt.bizarechat.data.repository;

import com.internship.pbt.bizarechat.data.datamodel.CreateFileBlobRequestModel;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateFileBlobsResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.requests.CreateFileBlobsRequest;
import com.internship.pbt.bizarechat.data.net.services.FileManagerService;

import rx.Observable;

public class FileDataRepository {

    FileManagerService fileManagerService;

    public FileDataRepository() {
        fileManagerService = RetrofitApi.getRetrofitApi().getFileManagerService();
    }

    public Observable<CreateFileBlobsResponse> uploadImageJpeg() {
        CreateFileBlobsRequest request = new CreateFileBlobsRequest();
        request.setBlob(new CreateFileBlobRequestModel(ApiConstants.CONTENT_TYPE_IMAGE_JPEG, "test"));
        fileManagerService.createFileBlobs(UserToken.getInstance().getToken(),
                request);
        return null;
    }

}
