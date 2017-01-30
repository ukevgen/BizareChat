package com.internship.pbt.bizarechat.presentation.model;

import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.presentation.AuthStore;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;


public class CurrentUser implements AuthStore {

    public static final String CURRENT_AVATAR = "AVATAR.jpg";

    private static CurrentUser INSTANCE;

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
        return cache.isAuthorized();
    }

    @Override
    public void setAuthorized(boolean authorized) {
        cache.putIsUserAuthorized(authorized);
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public Long getAvatarBlobId() {
        return cache.getAccountAvatarBlobId();
    }

    public void setAvatarBlobId(Long avatarBlobId) {
        cache.putAccountAvatarBlobId(avatarBlobId);
    }

    public String getCurrentPassword() {
        return cache.getCurrentPassword();
    }

    public void setCurrentPasswrod(String passwrod) {
        cache.putCurrentPassword(passwrod);
    }

    public String getCurrentEmail() {
        return cache.getCurrentEmail();
    }

    public void setCurrentEmail(String email) {
        cache.putCurrentEmail(email);
    }

    public Long getCurrentFacebookId() {
        return cache.getCurrentFacebookId();
    }

    public void setCurrentFacebookId(Long id) {
        cache.putCurrentFacebookId(id);
    }

    public void clearCurrentUser() {
        cache.deleteAllCache();
    }

    public Long getCurrentUserId() {
        return cache.getUserId();
    }

    public void setCurrentUserId(Long id) {
        cache.putUserId(id);
    }
}
