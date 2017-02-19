package com.internship.pbt.bizarechat.domain.events;




public class FirebaseTokenRefreshCompleteEvent {
    private String refreshedToken;

    public FirebaseTokenRefreshCompleteEvent(String refreshedToken) {
        this.refreshedToken = refreshedToken;
    }

    public String getRefreshedToken() {
        return refreshedToken;
    }
}
