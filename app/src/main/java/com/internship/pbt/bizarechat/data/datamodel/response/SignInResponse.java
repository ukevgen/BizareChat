package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
*
* THIS IS MODEL TO
*
 */

public class SignInResponse {

    @SerializedName("blob_id")
    @Expose
    private Object blobId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("custom_parameters")
    @Expose
    private Object customParameters;

    @SerializedName("email")
    @Expose
    private Object email;

    @SerializedName("external_user_id")
    @Expose
    private Integer externalUserId;

    @SerializedName("facebook_id")
    @Expose
    private Object facebookId;

    @SerializedName("full_name")
    @Expose
    private Object fullName;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("last_request_at")
    @Expose
    private String lastRequestAt;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("phone")
    @Expose
    private Object phone;

    @SerializedName("twitter_id")
    @Expose
    private Object twitterId;

    @SerializedName("twitter_digits_id")
    @Expose
    private Object twitterDigitsId;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("website")
    @Expose
    private Object website;

    @SerializedName("user_tags")
    @Expose
    private String userTags;


    public Object getBlobId() {
        return blobId;
    }

    public void setBlobId(Object blobId) {
        this.blobId = blobId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(Object customParameters) {
        this.customParameters = customParameters;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(Integer externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Object getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Object facebookId) {
        this.facebookId = facebookId;
    }

    public Object getFullName() {
        return fullName;
    }

    public void setFullName(Object fullName) {
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastRequestAt() {
        return lastRequestAt;
    }

    public void setLastRequestAt(String lastRequestAt) {
        this.lastRequestAt = lastRequestAt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(Object twitterId) {
        this.twitterId = twitterId;
    }

    public Object getTwitterDigitsId() {
        return twitterDigitsId;
    }

    public void setTwitterDigitsId(Object twitterDigitsId) {
        this.twitterDigitsId = twitterDigitsId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public String getUserTags() {
        return userTags;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

}