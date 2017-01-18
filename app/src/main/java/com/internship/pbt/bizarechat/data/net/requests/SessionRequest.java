package com.internship.pbt.bizarechat.data.net.requests;

/**
 * Use this POJO to create session request to API.
 */

public class SessionRequest {
    private String application_id;
    private String auth_key;
    private String timestamp;
    private String nonce;
    private String signature;

    public SessionRequest(String application_id, String auth_key, String timestamp, String nonce, String signature) {
        this.application_id = application_id;
        this.auth_key = auth_key;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getApplication_id() {
        return application_id;
    }

    @Override public String toString() {
        return "SessionRequest{" +
                "application_id='" + application_id + '\'' +
                ", auth_key='" + auth_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
