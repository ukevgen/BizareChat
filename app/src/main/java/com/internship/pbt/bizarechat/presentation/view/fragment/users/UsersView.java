package com.internship.pbt.bizarechat.presentation.view.fragment.users;


import com.arellomobile.mvp.MvpView;

public interface UsersView extends MvpView{
    void showLoading();

    void hideLoading();

    void showNetworkError();

    void showAloneMessage();
}
