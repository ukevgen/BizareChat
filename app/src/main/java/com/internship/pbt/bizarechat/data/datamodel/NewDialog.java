package com.internship.pbt.bizarechat.data.datamodel;


public class NewDialog {
    private int type;
    private String chatName;
    private String occupants;
    private int blobId;

    public NewDialog(int type, String chatName, String occupants, int blobId) {
        this.type = type;
        this.chatName = chatName;
        this.occupants = occupants;
        this.blobId = blobId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getOccupants() {
        return occupants;
    }

    public void setOccupants(String occupants) {
        this.occupants = occupants;
    }

    public int getBlobId() {
        return blobId;
    }

    public void setBlobId(int blobId) {
        this.blobId = blobId;
    }
}
