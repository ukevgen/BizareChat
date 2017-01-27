package com.internship.pbt.bizarechat.domain.model;

public class UserLoginResponse {

    private Integer blobId;

    private String createdAt;

    private Object customParameters;

    private String email;

    private Integer externalUserId;

    private Integer facebookId;

    private String fullName;

    private Integer id;

    private String lastRequestAt;

    private String login;

    private String phone;

    private String updatedAt;

    private String website;

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
