package com.internship.pbt.bizarechat.presentation;

public interface AuthStore {

    boolean isAuthorized();

    void setAuthorized(boolean status);
}
