package com.internship.pbt.bizarechat.presentation.model;

import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.presentation.AuthStore;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;


public class CurrentUser implements AuthStore {

    public static final String CURRENT_AVATAR = "AVATAR";

    private static CurrentUser INSTANCE;

    private Boolean isAuthorized = null;

    private String avatarBlobId;

    private String facebookToken;

    private CacheSharedPreferences cache;


    private CurrentUser() {
        super();
        cache = BizareChatApp.getInstance().getCache();
    }

    public static CurrentUser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CurrentUser();

        return INSTANCE;
    }

    @Override
    public boolean isAuthorized() {
        if (isAuthorized == null)
            return cache.isAuthorized();
        else
            return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        cache.putIsUserAuthorized(authorized);
        isAuthorized = authorized;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public String getAvatarBlobId() {
        if (avatarBlobId == null)
            return cache.getAccountAvatarBlobId();
        else
            return avatarBlobId;
    }

    public void setAvatarBlobId(String avatarBlobId) {
        cache.putAccountAvatarBlobId(avatarBlobId);
        this.avatarBlobId = avatarBlobId;
    }
}
