package com.internship.pbt.bizarechat.presentation.model;

public class FacebookLinkInform {

    private String fullName;

    private String userId;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "\\FullName=" + fullName + "\n" +
                "\\UserId="+userId+"\n";
    }
}
