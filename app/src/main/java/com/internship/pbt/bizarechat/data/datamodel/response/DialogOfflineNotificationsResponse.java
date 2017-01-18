package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.DialogOfflineNotificationsModel;

public class DialogOfflineNotificationsResponse {

    @SerializedName("notifications")
    @Expose
    private DialogOfflineNotificationsModel notifications;

    public DialogOfflineNotificationsModel getNotifications() {
        return notifications;
    }

    public void setNotifications(DialogOfflineNotificationsModel notifications) {
        this.notifications = notifications;
    }
}
