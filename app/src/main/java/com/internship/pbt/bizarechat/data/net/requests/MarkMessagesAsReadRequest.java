package com.internship.pbt.bizarechat.data.net.requests;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkMessagesAsReadRequest {
    @Expose
    @SerializedName("read")
    private int read;

    @Expose
    @SerializedName("chat_dialog_id")
    private String dialogId;

    public MarkMessagesAsReadRequest(int read, String dialogId) {
        this.read = read;
        this.dialogId = dialogId;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }
}
