package com.internship.pbt.bizarechat.data.net.requests;

import com.internship.pbt.bizarechat.data.datamodel.request.UserRequestModel;

public class SessionWithAuthRequest {

    private String application_id;
    private String auth_key;
    private String timestamp;
    private String nonce;
    private String signature;
    private UserRequestModel userModel;

    public SessionWithAuthRequest(String application_id,
                                  String auth_key,
                                  String timestamp,
                                  String nonce,
                                  String signature,
                                  UserRequestModel userModel) {

        this.application_id = application_id;
        this.auth_key = auth_key;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
        this.userModel = userModel;
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

    public UserRequestModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserRequestModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public String toString() {
        return "SessionWithAuthRequest{" +
                "application_id='" + application_id + '\'' +
                ", auth_key='" + auth_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                ", username='" + userModel.getLogin() + "\'" +
                ", userpassword='" + userModel.getPassword() + "\'" +
                '}';
    }
}
