package com.internship.pbt.bizarechat.data.datamodel;


public class NewDialog {
    private int type;
    private String name;
    private String occupants_ids;
    private String photo;

    public NewDialog(int type, String name, String occupants_ids, String photo) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}


