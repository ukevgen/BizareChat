package com.internship.pbt.bizarechat.data.net.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Use this POJO to create session request to API.
 */

public class SessionRequest {
    @SerializedName("application_id")
    @Expose
    private String applicationId;

    @SerializedName("auth_key")
    @Expose
    private String authKey;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("nonce")
    @Expose
    private String nonce;

    @SerializedName("signature")
    @Expose
    private String signature;

    public SessionRequest(String applicationId, String authKey, String timestamp, String nonce, String signature) {
        this.applicationId = applicationId;
        this.authKey = authKey;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getApplicationId() {
        return applicationId;
    }

    @Override
    public String toString() {
        return "SessionRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", authKey='" + authKey + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
