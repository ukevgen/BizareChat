package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "User")
public class UserModel {
    @Id(autoincrement = true)
    private long dbId;

    @Property(nameInDb = "user_id")
    @SerializedName("id")
    @Expose
    private Integer userId;

    @Property(nameInDb = "full_name")
    @SerializedName("full_name")
    @Expose
    private String fullName;

    @Property(nameInDb = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @Property(nameInDb = "login")
    @SerializedName("login")
    @Expose
    private String login;

    @Property(nameInDb = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;

    @Property(nameInDb = "website")
    @SerializedName("website")
    @Expose
    private String website;

    @Property(nameInDb = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @Property(nameInDb = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @Property(nameInDb = "last_request_at")
    @SerializedName("last_request_at")
    @Expose
    private String lastRequestAt;

    @Property(nameInDb = "external_user_id")
    @SerializedName("external_user_id")
    @Expose
    private Integer externalUserId;

    @Property(nameInDb = "facebook_id")
    @SerializedName("facebook_id")
    @Expose
    private Long facebookId;

    @Property(nameInDb = "twitter_id")
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;

    @Property(nameInDb = "twitter_digits_id")
    @SerializedName("twitter_digits_id")
    @Expose
    private Integer twitterDigitsId;

    @Property(nameInDb = "blob_id")
    @SerializedName("blob_id")
    @Expose
    private Integer blobId;

    @Property(nameInDb = "custom_data")
    @SerializedName("custom_data")
    @Expose
    private String customData;

    @Property(nameInDb = "user_tags")
    @SerializedName("user_tags")
    @Expose
    private String userTags;

    @Generated(hash = 1906899707)
    public UserModel(long dbId, Integer userId, String fullName, String email,
            String login, String phone, String website, String createdAt,
            String updatedAt, String lastRequestAt, Integer externalUserId,
            Long facebookId, String twitterId, Integer twitterDigitsId,
            Integer blobId, String customData, String userTags) {
        this.dbId = dbId;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.login = login;
        this.phone = phone;
        this.website = website;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastRequestAt = lastRequestAt;
        this.externalUserId = externalUserId;
        this.facebookId = facebookId;
        this.twitterId = twitterId;
        this.twitterDigitsId = twitterDigitsId;
        this.blobId = blobId;
        this.customData = customData;
        this.userTags = userTags;
    }

    @Generated(hash = 782181818)
    public UserModel() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getUserTags() {
        return userTags;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

    public long getId() {
        return this.dbId;
    }

    public void setId(long id) {
        this.dbId = id;
    }

    public long getDbId() {
        return this.dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

}
