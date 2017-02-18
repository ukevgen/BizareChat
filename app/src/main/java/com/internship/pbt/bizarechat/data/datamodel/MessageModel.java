package com.internship.pbt.bizarechat.data.datamodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(nameInDb = "Message")
public class MessageModel {
    @Id(autoincrement = true)
    private long dbId;
    @Property(nameInDb = "message_id")
    @SerializedName("_id")
    @Expose
    private String messageId;
    @Property(nameInDb = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Property(nameInDb = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @Convert(converter = AttachmentsConverter.class, columnType = String.class)
    @Property(nameInDb = "attachments")
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;
    @Convert(converter = OccupantsIdsConverter.class, columnType = String.class)
    @Property(nameInDb = "read_ids")
    @SerializedName("read_ids")
    @Expose
    private List<Integer> readIds = null;
    @Convert(converter = OccupantsIdsConverter.class, columnType = String.class)
    @Property(nameInDb = "delivered_ids")
    @SerializedName("delivered_ids")
    @Expose
    private List<Integer> deliveredIds = null;
    @Property(nameInDb = "chat_dialog_id")
    @SerializedName("chat_dialog_id")
    @Expose
    private String chatDialogId;
    @Property(nameInDb = "date_sent")
    @SerializedName("date_sent")
    @Expose
    private long dateSent;
    @Property(nameInDb = "message")
    @SerializedName("message")
    @Expose
    private String message;
    @Property(nameInDb = "recipient_id")
    @SerializedName("recipient_id")
    @Expose
    private Integer recipientId;
    @Property(nameInDb = "sender_id")
    @SerializedName("sender_id")
    @Expose
    private Integer senderId;
    @Property(nameInDb = "read")
    @SerializedName("read")
    @Expose
    private Integer read;

    @Generated(hash = 1965344788)
    public MessageModel(long dbId, String messageId, String createdAt, String updatedAt,
            List<Object> attachments, List<Integer> readIds, List<Integer> deliveredIds,
            String chatDialogId, long dateSent, String message, Integer recipientId, Integer senderId,
            Integer read) {
        this.dbId = dbId;
        this.messageId = messageId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.attachments = attachments;
        this.readIds = readIds;
        this.deliveredIds = deliveredIds;
        this.chatDialogId = chatDialogId;
        this.dateSent = dateSent;
        this.message = message;
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.read = read;
    }

    @Generated(hash = 1699352037)
    public MessageModel() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public long getDateSent() {
        return dateSent;
    }

    public void setDateSent(long dateSent) {
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

    public long getId() {
        return this.dbId;
    }

    public void setId(long id) {
        this.dbId = id;
    }

    public long getDbId() {
        return this.dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public static class OccupantsIdsConverter implements PropertyConverter<List<Integer>, String> {
        @Override
        public List<Integer> convertToEntityProperty(String databaseValue) {
            if(databaseValue == null)
                return null;

            List<Integer> result = new ArrayList<>();
            databaseValue = databaseValue.substring(1, databaseValue.length()-1);

            for(String entry : databaseValue.split("\\s*,\\s*")){
                result.add(Integer.parseInt(entry));
            }

            return result;
        }

        @Override
        public String convertToDatabaseValue(List<Integer> entityProperty) {
            return entityProperty.toString();
        }
    }

    public static class AttachmentsConverter implements PropertyConverter<List<Object>, String> {
        @Override
        public List<Object> convertToEntityProperty(String databaseValue) {
            return null;
        }

        @Override
        public String convertToDatabaseValue(List<Object> entityProperty) {
            return null;
        }
    }
}
