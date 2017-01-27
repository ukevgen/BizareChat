package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFileBlobRequestModel {

    @SerializedName("content_type")
    @Expose
    private String contentType;

    @SerializedName("name")
    @Expose
    private String name;

    public CreateFileBlobRequestModel(String contentType, String name) {
        this.contentType = contentType;
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
