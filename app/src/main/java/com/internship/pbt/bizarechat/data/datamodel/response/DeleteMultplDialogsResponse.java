package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.NotFound;
import com.internship.pbt.bizarechat.data.datamodel.SuccessfullyDeleted;
import com.internship.pbt.bizarechat.data.datamodel.WrongPermissions;

public class DeleteMultplDialogsResponse {

    @SerializedName("SuccessfullyDeleted")
    @Expose
    private SuccessfullyDeleted successfullyDeleted;

    @SerializedName("WrongPermissions")
    @Expose
    private WrongPermissions wrongPermissions;

    @SerializedName("NotFound")
    @Expose
    private NotFound notFound;


    public SuccessfullyDeleted getSuccessfullyDeleted() {
        return successfullyDeleted;
    }

    public void setSuccessfullyDeleted(SuccessfullyDeleted successfullyDeleted) {
        this.successfullyDeleted = successfullyDeleted;
    }

    public WrongPermissions getWrongPermissions() {
        return wrongPermissions;
    }

    public void setWrongPermissions(WrongPermissions wrongPermissions) {
        this.wrongPermissions = wrongPermissions;
    }

    public NotFound getNotFound() {
        return notFound;
    }

    public void setNotFound(NotFound notFound) {
        this.notFound = notFound;
    }
}
