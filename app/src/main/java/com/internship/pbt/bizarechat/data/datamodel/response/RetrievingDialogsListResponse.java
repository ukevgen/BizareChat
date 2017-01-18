package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.DialogInfModel;

import java.util.List;

public class RetrievingDialogsListResponse {

    @SerializedName("limit")
    @Expose
    private Integer limit;

    @SerializedName("skip")
    @Expose
    private Integer skip;

    @SerializedName("items")
    @Expose
    private List<DialogInfModel> items = null;


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

    public List<DialogInfModel> getItems() {
        return items;
    }

    public void setItems(List<DialogInfModel> items) {
        this.items = items;
    }
}
