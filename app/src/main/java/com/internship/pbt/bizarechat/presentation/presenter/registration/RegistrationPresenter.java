package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.content.Intent;
import android.net.Uri;

import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
import com.internship.pbt.bizarechat.presentation.presenter.LoadDataPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import java.io.File;

public interface RegistrationPresenter extends Presenter, LoadDataPresenter{

    void showErrorInvalidPassword();

    void setRegistrationView(RegistrationView registerView);

    void showErrorInvalidEmail();

    void showErrorInvalidPhoneNumber();

    void hideErrorsInvalid();

    void validateInformation(SignUpUserM model, String password);

    void onRegistrationSuccess();

    void facebookLink(LoginResult loginResult);

    void refreshLinkedInfInView(FacebookLinkInform linkInform);

    void registrationRequest(SignUpUserM model);

    void showErrorPasswordLength();

    void verifyAndLoadAvatar(Uri uri);

    void verifyAndLoadAvatar(File file);

    void showErrorPasswordConfirm();

    void createFormatWatcher();

    void uploadAvatar();

    void logoutFacebookSdk();

    void setCallbackToLoginFacebookButton();

    void setOnActivityResultInFacebookCallback(int requestCode, int resultCode, Intent data);

}