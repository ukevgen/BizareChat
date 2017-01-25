package com.internship.pbt.bizarechat.data.net.requests;

/**
 * Created by ukevgen on 24.01.2017.
 */

public class SignUpRequestModel {
    private String password;
    private String email;
    private int blob_id;
    private String full_name;
    private String phone;
    private String website;


    public SignUpRequestModel(String password, String email, int blob_id, String full_name,
                              String phone, String website) {
        this.password = password;
        this.email = email;
        this.blob_id = blob_id;
        this.full_name = full_name;
        this.phone = phone;
        this.website = website;
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

    public int getBlob_id() {
        return blob_id;
    }

    public void setBlob_id(int blob_id) {
        this.blob_id = blob_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", blob_id=" + blob_id +
                ", full_name='" + full_name + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
