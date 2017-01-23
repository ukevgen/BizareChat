package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFileBlobsResponse {

    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public class Blob {

        @SerializedName("blob_status")
        @Expose
        private Object blobStatus;
        @SerializedName("content_type")
        @Expose
        private String contentType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("last_read_access_ts")
        @Expose
        private Object lastReadAccessTs;
        @SerializedName("lifetime")
        @Expose
        private Integer lifetime;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("public")
        @Expose
        private Boolean _public;
        @SerializedName("ref_count")
        @Expose
        private Integer refCount;
        @SerializedName("set_completed_at")
        @Expose
        private Object setCompletedAt;
        @SerializedName("size")
        @Expose
        private Object size;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("blob_object_access")
        @Expose
        private BlobObjectAccess blobObjectAccess;

        public Object getBlobStatus() {
            return blobStatus;
        }

        public void setBlobStatus(Object blobStatus) {
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getLastReadAccessTs() {
            return lastReadAccessTs;
        }

        public void setLastReadAccessTs(Object lastReadAccessTs) {
            this.lastReadAccessTs = lastReadAccessTs;
        }

        public Integer getLifetime() {
            return lifetime;
        }

        public void setLifetime(Integer lifetime) {
            this.lifetime = lifetime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getPublic() {
            return _public;
        }

        public void setPublic(Boolean _public) {
            this._public = _public;
        }

        public Integer getRefCount() {
            return refCount;
        }

        public void setRefCount(Integer refCount) {
            this.refCount = refCount;
        }

        public Object getSetCompletedAt() {
            return setCompletedAt;
        }

        public void setSetCompletedAt(Object setCompletedAt) {
            this.setCompletedAt = setCompletedAt;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
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


    public class BlobObjectAccess {

        @SerializedName("blob_id")
        @Expose
        private Integer blobId;
        @SerializedName("expires")
        @Expose
        private String expires;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("object_access_type")
        @Expose
        private String objectAccessType;
        @SerializedName("params")
        @Expose
        private String params;

        public Integer getBlobId() {
            return blobId;
        }

        public void setBlobId(Integer blobId) {
            this.blobId = blobId;
        }

        public String getExpires() {
            return expires;
        }

        public void setExpires(String expires) {
            this.expires = expires;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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
