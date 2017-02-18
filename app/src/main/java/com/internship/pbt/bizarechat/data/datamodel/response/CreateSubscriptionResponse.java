package com.internship.pbt.bizarechat.data.datamodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateSubscriptionResponse {
    @SerializedName("subscription")
    @Expose
    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public static class Subscription{
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("notification_channel")
        @Expose
        private NotificationChannel notificationChannel;
        @SerializedName("device")
        @Expose
        private Device device;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public NotificationChannel getNotificationChannel() {
            return notificationChannel;
        }

        public void setNotificationChannel(NotificationChannel notificationChannel) {
            this.notificationChannel = notificationChannel;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }
    }

    public static class NotificationChannel {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Device {

        @SerializedName("udid")
        @Expose
        private String udid;
        @SerializedName("platform")
        @Expose
        private Platform platform;

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public Platform getPlatform() {
            return platform;
        }

        public void setPlatform(Platform platform) {
            this.platform = platform;
        }
    }

    public static class Platform {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
