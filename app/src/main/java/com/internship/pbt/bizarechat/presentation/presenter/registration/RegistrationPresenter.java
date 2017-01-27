package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.net.Uri;

import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.domain.model.signup.ResponseSignUpModel;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

public interface RegistrationPresenter extends Presenter {

    void showErrorInvalidPassword();

    void setRegistrationView(RegistrationView registerView);

    void showErrorInvalidEmail();

    void showErrorInvalidPhoneNumber();

    void hideErrorsInvalid();

    void validateInformation(SignUpUserM model, String password);

    void onRegistrationSuccess(ResponseSignUpModel model);

    void facebookLink(LoginResult loginResult);

    void refreshLinkedInfInView(FacebookLinkInform linkInform);

    void registrationRequest(SignUpUserM model);

    void showErrorPasswordLength();

    void verifyAndLoadAvatar(Uri uri);

    void showErrorPasswordConfirm();

    void createFormatWatcher();

    void uploadAvatar();

}