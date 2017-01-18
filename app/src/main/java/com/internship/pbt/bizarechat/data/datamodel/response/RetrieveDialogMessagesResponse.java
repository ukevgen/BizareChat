package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.MessageInfModel;

import java.util.List;

public class RetrieveDialogMessagesResponse {

    @SerializedName("skip")
    @Expose
    private Integer skip;

    @SerializedName("limit")
    @Expose
    private Integer limit;

    @SerializedName("items")
    @Expose
    private List<MessageInfModel> items = null;


    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<MessageInfModel> getItems() {
        return items;
    }

    public void setItems(List<MessageInfModel> items) {
        this.items = items;
    }

}

