package com.internship.pbt.bizarechat.presentation.view.fragment.userinfo;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;


@StateStrategyType(SkipStrategy.class)
public interface UserInfoView extends MvpView {
    void startSendEmail(String email);

    void startDialPhoneNumber(String number);

    void startOpenWebsite(String website);

    void showLoading();

    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void showChatRoom(DialogModel dialogModel);
}
