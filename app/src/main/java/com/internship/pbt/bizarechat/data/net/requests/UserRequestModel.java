package com.internship.pbt.bizarechat.data.net.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequestModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public UserRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login " + email + "\n" +
                "Password " + password + "\n";
    }
}
