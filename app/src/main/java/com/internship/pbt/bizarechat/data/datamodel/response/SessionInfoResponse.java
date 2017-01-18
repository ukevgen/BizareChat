package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 /*
*
* THIS IS MODEL TO
*
 */

public class SessionInfoResponse {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("application_id")
    @Expose
    private Integer applicationId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("device_id")
    @Expose
    private Integer deviceId;

    @SerializedName("nonce")
    @Expose
    private Integer nonce;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("ts")
    @Expose
    private Integer ts;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("user_id")
    @Expose
    private Integer userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getNonce() {
        return nonce;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
