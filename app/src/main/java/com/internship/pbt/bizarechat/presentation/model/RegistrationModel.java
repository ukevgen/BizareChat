package com.internship.pbt.bizarechat.presentation.model;

import android.os.Bundle;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;

public class RegistrationModel implements SignUpModel {

    private RegistrationPresenter presenter;

    @Override
    public void getFacebookLink(LoginResult loginResult) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", "first_name, email, id");
        Log.d("123", "loginResult " + loginResult.getAccessToken().getToken() + "  " + loginResult.getAccessToken().getUserId() + "\n");
        Log.d("123", "Profile " + Profile.getCurrentProfile().getId() + " ");
        FacebookLinkInform inform = new FacebookLinkInform();
        GraphRequest gr = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), (me, response) -> {
                    if (response.getError() != null) {
                        Log.d("123", "Model error " + response.getError().toString());

                    } else {
                        inform.setEmail(me.optString("email"));
                        inform.setUserId(me.optString("user_id"));

                        Log.d("123", inform.toString() + " NAME " + me.optString("first_name"));
                    }
                });
        gr.setParameters(bundle);
        gr.executeAsync();

    }

    @Override
    public void setPresenter(RegistrationPresenter presenter) {
        this.presenter = presenter;
    }
}
