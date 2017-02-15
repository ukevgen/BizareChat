package com.internship.pbt.bizarechat.presentation.model;

import android.graphics.Bitmap;

/**
 * Created by user on 14.02.2017.
 */

public class ContactsFriends {
    private String name,
            email;
    private String photoId;
    private boolean checked = false;
    private Bitmap userPic;

    public ContactsFriends() {
    }

    public Bitmap getUserPic() {
        return userPic;
    }

    public void setUserPic(Bitmap userPic) {
        this.userPic = userPic;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
