package com.internship.pbt.bizarechat.data.net.requests.dialog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DialogUpdateRequestModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private Integer photoBlobId;
    @SerializedName("pull_all")
    @Expose
    private PullAll pullAll;
    @SerializedName("push_all")
    @Expose
    private PushAll pushAll;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhotoBlobId() {
        return photoBlobId;
    }

    public void setPhotoBlobId(Integer photoBlobId) {
        this.photoBlobId = photoBlobId;
    }

    public PullAll getPullAll() {
        return pullAll;
    }

    public void setPullAll(PullAll pullAll) {
        this.pullAll = pullAll;
    }

    public PushAll getPushAll() {
        return pushAll;
    }

    public void setPushAll(PushAll pushAll) {
        this.pushAll = pushAll;
    }

    public class PullAll {

        @SerializedName("occupants_ids")
        @Expose
        private List<Integer> occupantsIds = null;

        public List<Integer> getOccupantsIds() {
            return occupantsIds;
        }

        public void setOccupantsIds(List<Integer> occupantsIds) {
            this.occupantsIds = occupantsIds;
        }
    }

    public class PushAll {

        @SerializedName("occupants_ids")
        @Expose
        private List<Integer> occupantsIds = null;

        public List<Integer> getOccupantsIds() {
            return occupantsIds;
        }

        public void setOccupantsIds(List<Integer> occupantsIds) {
            this.occupantsIds = occupantsIds;
        }
    }
}
