package com.internship.pbt.bizarechat.data.net.requests.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ukevgen on 26.01.2017.
 */

public class SignUpRequestM {
    @SerializedName("user")
    @Expose
    private SignUpUserM user;

    public SignUpUserM getUser() {
        return user;
    }

    public void setUser(SignUpUserM user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SignUpResponseModel{" +
                "user=" + user +
                '}';
    }
}
