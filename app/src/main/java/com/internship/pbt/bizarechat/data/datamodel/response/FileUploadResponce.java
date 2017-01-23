package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileUploadResponce {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("file_id")
    @Expose
    private String fileId;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("content_type")
    @Expose
    private String contentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

