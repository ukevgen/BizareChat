package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.UserItemModel;

import java.util.List;

public class RetrievingUsersListResponse {

    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    @SerializedName("per_page")
    @Expose
    private Integer perPage;

    @SerializedName("total_entries")
    @Expose
    private Integer totalEntries;

    @SerializedName("items")
    @Expose
    private List<UserItemModel> items = null;


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public List<UserItemModel> getItems() {
        return items;
    }

    public void setItems(List<UserItemModel> items) {
        this.items = items;
    }
}
