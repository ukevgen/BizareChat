package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.net.Uri;

import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
import com.internship.pbt.bizarechat.presentation.model.InformationOnCheck;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

public interface RegistrationPresenter extends Presenter {

    void showErrorInvalidPassword();

    void setRegistrationView(RegistrationView registerView);

    void showErrorInvalidEmail();

    void showErrorInvalidPhoneNumber();

    void hideErrorsInvalid();

    void validateInformation(InformationOnCheck informationOnCheck);

    void onRegistrationSuccess();

    void facebookLink(LoginResult loginResult);

    void refreshLinkedInfInView(FacebookLinkInform linkInform);

    void registrationRequest(InformationOnCheck informationOnCheck);

    void showErrorPasswordLength();

    void verifyAndLoadAvatar(Uri uri);

    void showErrorPasswordConfirm();

    void createFormatWatcher();

    void uploadAvatar();

}