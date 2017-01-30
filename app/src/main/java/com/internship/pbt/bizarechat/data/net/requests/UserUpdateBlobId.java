package com.internship.pbt.bizarechat.data.net.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateBlobId {

    @SerializedName("user")
    @Expose
    private User user;

    public UserUpdateBlobId(Integer blobId) {
        if(user == null)
            user = new User(blobId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {
        @SerializedName("blob_id")
        @Expose
        private Integer blobId;

        public User(Integer blobId) {
            this.blobId = blobId;
        }

        public Integer getBlobId() {
            return blobId;
        }

        public void setBlobId(Integer blobId) {
            this.blobId = blobId;
        }
    }
}
