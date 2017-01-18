package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageInfModel {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;

    @SerializedName("read_ids")
    @Expose
    private List<Integer> readIds = null;

    @SerializedName("delivered_ids")
    @Expose
    private List<Integer> deliveredIds = null;

    @SerializedName("chat_dialog_id")
    @Expose
    private String chatDialogId;

    @SerializedName("date_sent")
    @Expose
    private Integer dateSent;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("recipient_id")
    @Expose
    private Integer recipientId;

    @SerializedName("sender_id")
    @Expose
    private Integer senderId;

    @SerializedName("read")
    @Expose
    private Integer read;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public List<Integer> getReadIds() {
        return readIds;
    }

    public void setReadIds(List<Integer> readIds) {
        this.readIds = readIds;
    }

    public List<Integer> getDeliveredIds() {
        return deliveredIds;
    }

    public void setDeliveredIds(List<Integer> deliveredIds) {
        this.deliveredIds = deliveredIds;
    }

    public String getChatDialogId() {
        return chatDialogId;
    }

    public void setChatDialogId(String chatDialogId) {
        this.chatDialogId = chatDialogId;
    }

    public Integer getDateSent() {
        return dateSent;
    }

    public void setDateSent(Integer dateSent) {
        this.dateSent = dateSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }
}