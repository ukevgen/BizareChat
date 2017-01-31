package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("last_request_at")
    @Expose
    private String lastRequestAt;

    @SerializedName("external_user_id")
    @Expose
    private Integer externalUserId;

    @SerializedName("facebook_id")
    @Expose
    private Long facebookId;

    @SerializedName("twitter_id")
    @Expose
    private String twitterId;

    @SerializedName("twitter_digits_id")
    @Expose
    private Integer twitterDigitsId;

    @SerializedName("blob_id")
    @Expose
    private Integer blobId;

    @SerializedName("custom_data")
    @Expose
    private Object customData;

    @SerializedName("user_tags")
    @Expose
    private String userTags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastRequestAt() {
        return lastRequestAt;
    }

    public void setLastRequestAt(String lastRequestAt) {
        this.lastRequestAt = lastRequestAt;
    }

    public Integer getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(Integer externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
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

    public Integer getBlobId() {
        return blobId;
    }

    public void setBlobId(Integer blobId) {
        this.blobId = blobId;
    }

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

    public String getUserTags() {
        return userTags;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

}
