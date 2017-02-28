package com.internship.pbt.bizarechat.domain.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ResponseSignUpModel {

    @SerializedName("user")
    @Expose
    private ResponseSignUpUserModel user;

    public ResponseSignUpUserModel getUser() {
        return user;
    }

    public void setUser(ResponseSignUpUserModel user) {
        this.user = user;
    }

}

