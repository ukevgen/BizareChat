package com.internship.pbt.bizarechat.presentation.presenter.userinfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.userinfo.UserInfoView;


@InjectViewState
public class UserInfoPresenter extends MvpPresenter<UserInfoView> {
    public void sendEmail(String email){
        if(email != null && !email.isEmpty()){
            getViewState().startSendEmail(email);
        }
    }

    public void dialPhoneNumber(String phone){
        if(phone != null && !phone.isEmpty()){
            getViewState().startDialPhoneNumber(phone);
        }
    }

    public void openWebsite(String website){
        if(website != null && !website.isEmpty()){
            getViewState().startOpenWebsite(website);
        }
    }
}
