package com.internship.pbt.bizarechat.presentation.view.fragment.userinfo;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(SkipStrategy.class)
public interface UserInfoView extends MvpView {
    void startSendEmail(String email);

    void startDialPhoneNumber(String number);

    void startOpenWebsite(String website);
}
