package com.internship.pbt.bizarechat.presentation.view.fragment.users;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

public interface UsersView extends MvpView{
    void showLoading();

    void hideLoading();

    void showNetworkError();

    void showAloneMessage();

    @StateStrategyType(SkipStrategy.class)
    void showUserInfo(UserModel user);
}
