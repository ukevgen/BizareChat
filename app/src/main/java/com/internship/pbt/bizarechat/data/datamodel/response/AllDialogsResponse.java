package com.internship.pbt.bizarechat.data.datamodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;

import java.util.List;


public class AllDialogsResponse {
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("dialogModels")
    @Expose
    private List<DialogModel> dialogModels = null;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public List<DialogModel> getDialogModels() {
        return dialogModels;
    }

    public void setDialogModels(List<DialogModel> dialogModels) {
        this.dialogModels = dialogModels;
    }
}
