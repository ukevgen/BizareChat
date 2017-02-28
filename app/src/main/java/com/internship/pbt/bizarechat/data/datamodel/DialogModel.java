package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity(nameInDb = "Dialog")
public class DialogModel {
    @Id
    @Property(nameInDb = "dialog_id")
    @SerializedName("_id")
    @Expose
    private String dialogId;
    @Property(nameInDb = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Property(nameInDb = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @Property(nameInDb = "last_message")
    @SerializedName("last_message")
    @Expose
    private String lastMessage;
    @Property(nameInDb = "last_message_date_sent")
    @SerializedName("last_message_date_sent")
    @Expose
    private long lastMessageDateSent;
    @Property(nameInDb = "last_message_user_id")
    @SerializedName("last_message_user_id")
    @Expose
    private int lastMessageUserId;
    @Property(nameInDb = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @Property(nameInDb = "photo")
    @SerializedName("photo")
    @Expose
    private Integer photo;
    @Property(nameInDb = "occupants_ids")
    @Convert(converter = OccupantsIdsConverter.class, columnType = String.class)
    @SerializedName("occupants_ids")
    @Expose
    private List<Integer> occupantsIds = null;
    @Property(nameInDb = "type")
    @SerializedName("type")
    @Expose
    private Integer type;
    @Property(nameInDb = "unread_messages_count")
    @SerializedName("unread_messages_count")
    @Expose
    private Integer unreadMessagesCount;
    @Property(nameInDb = "xmpp_room_jid")
    @SerializedName("xmpp_room_jid")
    @Expose
    private String xmppRoomJid;

    @Generated(hash = 165913975)
    public DialogModel(String dialogId, String createdAt, String updatedAt, String lastMessage, long lastMessageDateSent, int lastMessageUserId,
            String name, Integer photo, List<Integer> occupantsIds, Integer type, Integer unreadMessagesCount, String xmppRoomJid) {
        this.dialogId = dialogId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastMessage = lastMessage;
        this.lastMessageDateSent = lastMessageDateSent;
        this.lastMessageUserId = lastMessageUserId;
        this.name = name;
        this.photo = photo;
        this.occupantsIds = occupantsIds;
        this.type = type;
        this.unreadMessagesCount = unreadMessagesCount;
        this.xmppRoomJid = xmppRoomJid;
    }

    @Generated(hash = 174413608)
    public DialogModel() {
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastMessageDateSent() {
        return lastMessageDateSent;
    }

    public void setLastMessageDateSent(long lastMessageDateSent) {
        this.lastMessageDateSent = lastMessageDateSent;
    }

    public Integer getLastMessageUserId() {
        return lastMessageUserId;
    }

    public void setLastMessageUserId(Integer lastMessageUserId) {
        this.lastMessageUserId = lastMessageUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public List<Integer> getOccupantsIds() {
        return occupantsIds;
    }

    public void setOccupantsIds(List<Integer> occupantsIds) {
        this.occupantsIds = occupantsIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getXmppRoomJid() {
        return xmppRoomJid;
    }

    public void setXmppRoomJid(String xmppRoomJid) {
        this.xmppRoomJid = xmppRoomJid;
    }

    public void setLastMessageUserId(int lastMessageUserId) {
        this.lastMessageUserId = lastMessageUserId;
    }

    public static class OccupantsIdsConverter implements PropertyConverter<List<Integer>, String> {
        @Override
        public List<Integer> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null)
                return null;

            List<Integer> result = new ArrayList<>();
            databaseValue = databaseValue.substring(1, databaseValue.length() - 1);

            if (databaseValue.isEmpty()) return result;

            for (String entry : databaseValue.split("\\s*,\\s*")) {
                result.add(Integer.parseInt(entry));
            }

            return result;
        }

        @Override
        public String convertToDatabaseValue(List<Integer> entityProperty) {
            return entityProperty.toString();
        }
    }

    @Override
    public String toString() {
        return "DialogModel{" +
                " dialogId='" + dialogId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", lastMessageDateSent=" + lastMessageDateSent +
                ", lastMessageUserId=" + lastMessageUserId +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", occupantsIds=" + occupantsIds +
                ", type=" + type +
                ", unreadMessagesCount=" + unreadMessagesCount +
                ", xmppRoomJid='" + xmppRoomJid + '\'' +
                '}';
    }

    public String getLastMessageTime() {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        Format formatter = new SimpleDateFormat("dd/MM/yy");
        Date dt = new Date(lastMessageDateSent * 1000);

        String now = formatter.format(new Date());
        String messageDate = formatter.format(dt);
        if (now.equals(messageDate))
            return localDateFormat.format(dt).toString();
        else
            return messageDate;
    }
}
