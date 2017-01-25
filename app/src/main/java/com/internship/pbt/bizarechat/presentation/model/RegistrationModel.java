package com.internship.pbt.bizarechat.presentation.model;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;

public class RegistrationModel implements SignUpModel {

    private RegistrationPresenter presenter;

    @Override
    public void getFacebookLink(LoginResult loginResult) {
        Log.d("123", "loginResult " + loginResult.getAccessToken().getToken() + "  " + loginResult.getAccessToken().getUserId() + "\n");
        Log.d("123", "Profile " + Profile.getCurrentProfile().getId() + " ");
        FacebookLinkInform inform = new FacebookLinkInform();
        inform.setToken(AccessToken.getCurrentAccessToken().getUserId());

        if(inform.getToken() != null)
            presenter.refreshLinkedInfInView(inform);
    }

    @Override
    public void setPresenter(RegistrationPresenter presenter) {
        this.presenter = presenter;
    }
}
