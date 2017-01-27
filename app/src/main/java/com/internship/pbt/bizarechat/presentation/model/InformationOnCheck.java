package com.internship.pbt.bizarechat.presentation.model;

public class InformationOnCheck {

    private String mEmail;
    private String mPassword;
    private String passwordConf;
    private String mPhone;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPasswordConf() {
        return passwordConf;
    }

    public void setPasswordConf(String passwordConf) {
        this.passwordConf = passwordConf;
    }

    @Override
    public String toString() {
        return "ValidationInformation{" +
                "mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", passwordConf='" + passwordConf + '\'' +
                ", mPhone='" + mPhone + '\'' +
                '}';
    }
}
