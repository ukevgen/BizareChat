package com.internship.pbt.bizarechat.data.net.requests.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ukevgen on 26.01.2017.
 */

public class SignUpUserM {


    @SerializedName("password")
    @Expose
    private String password = null;
    @SerializedName("email")
    @Expose
    private String email = null;
    @SerializedName("full_name")
    @Expose
    private String fullName = null;
    @SerializedName("phone")
    @Expose
    private String phone = null;
    @SerializedName("website")
    @Expose
    private String website = null;
    @SerializedName("blob_id")
    @Expose
    private Integer blobId = null;
    @SerializedName("facebook_id")
    @Expose
    private Long facebookId = null;

    public Long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getBlobId() {
        return blobId;
    }

    public void setBlobId(Integer blobId) {
        this.blobId = blobId;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", blobId=" + blobId +
                '}';
    }
}

