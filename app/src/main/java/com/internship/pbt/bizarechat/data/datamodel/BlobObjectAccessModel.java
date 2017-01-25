package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlobObjectAccessModel {

    @SerializedName("blob_id")
    @Expose
    private Integer blobId;
    @SerializedName("expires")
    @Expose
    private String expires;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("object_access_type")
    @Expose
    private String objectAccessType;
    @SerializedName("params")
    @Expose
    private String params;

    public Integer getBlobId() {
        return blobId;
    }

    public void setBlobId(Integer blobId) {
        this.blobId = blobId;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectAccessType() {
        return objectAccessType;
    }

    public void setObjectAccessType(String objectAccessType) {
        this.objectAccessType = objectAccessType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
