package com.internship.pbt.bizarechat.data.net.requests;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateSubscriptionRequest {
    @SerializedName("notification_channels")
    @Expose
    private String notificationChannels;

    @SerializedName("push_token")
    @Expose
    private PushToken pushToken;

    @SerializedName("device")
    @Expose
    private Device device;

    public String getNotificationChannels() {
        return notificationChannels;
    }

    public void setNotificationChannels(String notificationChannels) {
        this.notificationChannels = notificationChannels;
    }

    public PushToken getPushToken() {
        return pushToken;
    }

    public void setPushToken(PushToken pushToken) {
        this.pushToken = pushToken;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public static class PushToken{
        @SerializedName("environment")
        @Expose
        private String environment;

        @SerializedName("client_identification_sequence")
        @Expose
        private String clientIdSequence;

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getClientIdSequence() {
            return clientIdSequence;
        }

        public void setClientIdSequence(String clientIdSequence) {
            this.clientIdSequence = clientIdSequence;
        }
    }

    public static class Device{
        @SerializedName("platform")
        @Expose
        private String platform;

        @SerializedName("udid")
        @Expose
        private String udid;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }
    }
}
