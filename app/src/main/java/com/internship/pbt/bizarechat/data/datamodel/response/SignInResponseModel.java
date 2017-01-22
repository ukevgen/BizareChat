package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponseModel {



    @SerializedName("blob_id")
    @Expose
    private Integer blobId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("custom_parameters")
    @Expose
    private Object customParameters;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("external_user_id")
    @Expose
    private Integer externalUserId;

    @SerializedName("facebook_id")
    @Expose
    private Integer facebookId;

    @SerializedName("full_name")
    @Expose
    private String fullName;

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
    private String phone;

    @SerializedName("twitter_id")
    @Expose
    private String twitterId;

    @SerializedName("twitter_digits_id")
    @Expose
    private Integer twitterDigitsId;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("user_tags")
    @Expose
    private String userTags;


    public Integer getBlobId() {
        return blobId;
    }

    public void setBlobId(Integer blobId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(Integer externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Integer getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Integer facebookId) {
        this.facebookId = facebookId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public Integer getTwitterDigitsId() {
        return twitterDigitsId;
    }

    public void setTwitterDigitsId(Integer twitterDigitsId) {
        this.twitterDigitsId = twitterDigitsId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUserTags() {
        return userTags;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

}