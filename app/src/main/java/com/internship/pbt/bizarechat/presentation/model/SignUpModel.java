package com.internship.pbt.bizarechat.presentation.model;

import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;

public interface SignUpModel {

    void getFacebookLink(LoginResult loginResult);

    void setPresenter(RegistrationPresenter presenter);
}
