package com.internship.pbt.bizarechat.data.datamodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

public class UserByIdResponse {
    @Expose
    @SerializedName("user")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
