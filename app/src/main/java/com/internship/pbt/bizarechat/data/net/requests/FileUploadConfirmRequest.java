package com.internship.pbt.bizarechat.data.net.requests;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileUploadConfirmRequest {
    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public static class Blob {
        @SerializedName("size")
        @Expose
        private String size;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
