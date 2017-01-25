package com.internship.pbt.bizarechat.presentation.model;

import com.internship.pbt.bizarechat.presentation.AuthStore;


public class CurrentUser implements AuthStore {

    private static CurrentUser INSTANCE;

    private boolean isAuthorized;

    private String facebookToken;

    private CurrentUser() {
        super();
    }

    public static CurrentUser getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new CurrentUser();

        return INSTANCE;
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }
}