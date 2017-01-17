package com.internship.pbt.bizarechat.presentation.view.fragment;

public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

}
