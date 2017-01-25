package com.internship.pbt.bizarechat.data.datamodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFileResponse {
    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public static class Blob{
        @SerializedName("blob_status")
        @Expose
        private String blobStatus;

        @SerializedName("content_type")
        @Expose
        private String contentType;

        @SerializedName("created_at")
        @Expose
        private String createdAt;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("last_read_access_ts")
        @Expose
        private String lastReadAccessTs;

        @SerializedName("lifetime")
        @Expose
        private String lifeTime;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("public")
        @Expose
        private boolean publik;

        @SerializedName("ref_count")
        @Expose
        private String refCount;

        @SerializedName("set_completed_at")
        @Expose
        private String setCompletedAt;

        @SerializedName("size")
        @Expose
        private String size;

        @SerializedName("uid")
        @Expose
        private String uid;

        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        @SerializedName("blob_object_access")
        @Expose
        private BlobObjectAccess blobObjectAccess;

        public String getBlobStatus() {
            return blobStatus;
        }

        public void setBlobStatus(String blobStatus) {
            this.blobStatus = blobStatus;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastReadAccessTs() {
            return lastReadAccessTs;
        }

        public void setLastReadAccessTs(String lastReadAccessTs) {
            this.lastReadAccessTs = lastReadAccessTs;
        }

        public String getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(String lifeTime) {
            this.lifeTime = lifeTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPublik() {
            return publik;
        }

        public void setPublik(boolean publik) {
            this.publik = publik;
        }

        public String getRefCount() {
            return refCount;
        }

        public void setRefCount(String refCount) {
            this.refCount = refCount;
        }

        public String getSetCompletedAt() {
            return setCompletedAt;
        }

        public void setSetCompletedAt(String setCompletedAt) {
            this.setCompletedAt = setCompletedAt;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public BlobObjectAccess getBlobObjectAccess() {
            return blobObjectAccess;
        }

        public void setBlobObjectAccess(BlobObjectAccess blobObjectAccess) {
            this.blobObjectAccess = blobObjectAccess;
        }
    }

    public static class BlobObjectAccess{
        @SerializedName("blobId")
        @Expose
        private String updatedAt;

        @SerializedName("expires")
        @Expose
        private String expires;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("object_access_type")
        @Expose
        private String objectAccessType;

        @SerializedName("params")
        @Expose
        private String params;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getExpires() {
            return expires;
        }

        public void setExpires(String expires) {
            this.expires = expires;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObjectAccessType() {
            return objectAccessType;
        }

        public void setObjectAccessType(String objectAccessType) {
            this.objectAccessType = objectAccessType;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }
    }
}
