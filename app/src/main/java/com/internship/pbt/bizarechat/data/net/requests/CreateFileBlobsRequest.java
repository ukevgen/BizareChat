package com.internship.pbt.bizarechat.data.net.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.CreateFileBlobRequestModel;

public class CreateFileBlobsRequest {

    @SerializedName("blob")
    @Expose
    private CreateFileBlobRequestModel blob;

    public CreateFileBlobRequestModel getBlob() {
        return blob;
    }

    public void setBlob(CreateFileBlobRequestModel blob) {
        this.blob = blob;
    }

}
