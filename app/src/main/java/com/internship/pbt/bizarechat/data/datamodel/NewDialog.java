package com.internship.pbt.bizarechat.data.datamodel;


public class NewDialog {
    private int type;
    private String name;
    private String occupants_ids;
    private int photo;

    public NewDialog(int type, String name, String occupants_ids, int photo) {
        this.type = type;
        this.name = name;
        this.occupants_ids = occupants_ids;
        this.photo = photo;
    }

    public NewDialog(int type, String name, String occupants_ids) {
        this.type = type;
        this.name = name;
        this.occupants_ids = occupants_ids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupants_ids() {
        return occupants_ids;
    }

    public void setOccupants_ids(String occupants_ids) {
        this.occupants_ids = occupants_ids;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}


