package com.internship.pbt.bizarechat.data.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessfullyDeleted {

    @SerializedName("ids")
    @Expose
    private List<String> ids = null;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
