package com.internship.pbt.bizarechat.data.net.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionWithAuthRequest {

    @SerializedName("application_id")
    @Expose
    private String application_id;

    @SerializedName("auth_key")
    @Expose
    private String auth_key;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("nonce")
    @Expose
    private String nonce;

    @SerializedName("signature")
    @Expose
    private String signature;

    @SerializedName("user")
    @Expose
    private UserRequestModel userRequestModel;


    public SessionWithAuthRequest(String application_id,
                                  String auth_key,
                                  String timestamp,
                                  String nonce,
                                  String signature,
                                  UserRequestModel userRequestModel) {

        this.application_id = application_id;
        this.auth_key = auth_key;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
        this.userRequestModel = userRequestModel;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UserRequestModel getUserRequestModel() {
        return userRequestModel;
    }

    public void setUserRequestModel(UserRequestModel userRequestModel) {
        this.userRequestModel = userRequestModel;
    }

    @Override
    public String toString() {
        return "SessionWithAuthRequest{" +
                "application_id='" + application_id + '\'' +
                ", auth_key='" + auth_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                ", username='" + userRequestModel.getEmail() + "\'" +
                ", userpassword='" + userRequestModel.getPassword() + "\'" +
                '}';
    }
}
