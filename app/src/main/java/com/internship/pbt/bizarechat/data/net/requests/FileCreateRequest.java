package com.internship.pbt.bizarechat.data.net.requests;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileCreateRequest {
    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public static class Blob{
        @SerializedName("content_type")
        @Expose
        private String contentType;

        @SerializedName("name")
        @Expose
        private String name;

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
}
