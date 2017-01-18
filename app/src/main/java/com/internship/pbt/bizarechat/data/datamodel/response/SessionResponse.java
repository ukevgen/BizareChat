package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.pbt.bizarechat.data.datamodel.Session;

public class SessionResponse {

    @SerializedName("session")
    @Expose
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
