package com.internship.pbt.bizarechat.presentation.model;

public class FacebookLinkInform {

    private String email;

    private String phoneNum;

    private String userId;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "\\Email=" + email + " \n" +
                "\\PhoneNumber=" + phoneNum + "\n" +
                "\\UserId="+userId+"\n";
    }
}
