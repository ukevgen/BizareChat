package com.internship.pbt.bizarechat.data.datamodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

import java.util.List;

public class AllUsersResponse {
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
    private List<Item> items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item{
        @SerializedName("user")
        @Expose
        private UserModel user;

        public UserModel getUser() {
            return user;
        }

        public void setUser(UserModel user) {
            this.user = user;
        }
    }
}
