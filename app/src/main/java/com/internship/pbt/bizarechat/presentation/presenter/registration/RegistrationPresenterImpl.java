package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.content.Context;
import android.net.Uri;

import com.internship.pbt.bizarechat.presentation.model.InformationOnCheck;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private final String TAG = "RegistrPresenterImpl";
    private Validator mValidator = new Validator();
    private RegistrationView mRegisterView;

    @Override
    public void setRegistrationView(RegistrationView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void showErrorInvalidPassword() {
        mRegisterView.showErrorInvalidPassword();
    }

    @Override
    public void showErrorInvalidEmail() {
<<<<<<< HEAD
=======
        Log.d("123", "Presenter showErrorInvalidEmail");
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c
        mRegisterView.showErrorInvalidEmail();
    }

    @Override
    public void showErrorInvalidPhoneNumber() {
        mRegisterView.showErrorInvalidPhone();
    }

    @Override
    public void showErrorPasswordLength() {
        mRegisterView.showErrorPasswordLength();
    }

    @Override
<<<<<<< HEAD
=======
    public void showErrorPasswordConfirm() {
        mRegisterView.showErrorPasswordConfirm();
    }

    @Override
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c
    public void hideErrorsInvalid() {
        mRegisterView.hideErrorInvalidEmail();
        mRegisterView.hideErrorInvalidPassword();
        mRegisterView.hideErrorInvalidPhone();
        mRegisterView.hideErrorPasswordConfirm();
    }

    @Override
    public void showViewLoading() {
        mRegisterView.showLoading();
    }

    @Override
    public void hideViewLoading() {
        mRegisterView.hideLoading();
    }

    @Override
<<<<<<< HEAD
    public void validateInformation(InformationOnCheck informationOnCheck) {

        this.hideErrorsInvalid();
=======
    public void validateInformation(ValidationInformation validationInformation) {
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c

        boolean isValidationSuccess = true;
        if (!mValidator.isValidEmail(informationOnCheck.getEmail())) {
            isValidationSuccess = false;
            this.showErrorInvalidEmail();
        }
        if (!mValidator.isValidPassword(informationOnCheck.getPassword())) {
            isValidationSuccess = false;
            this.showErrorInvalidPassword();
        }
        if (!mValidator.isValidPhoneNumber(informationOnCheck.getPhone())) {
            isValidationSuccess = false;
            this.showErrorInvalidPhoneNumber();
        }
        if (!mValidator.isPasswordLengthMatches(informationOnCheck.getPassword())) {
            isValidationSuccess = false;
            this.showErrorPasswordLength();
        }
        if (!mValidator.isPasswordMatch(validationInformation.getPassword(),
                validationInformation.getPasswordConf())) {
            isSuccess = false;
            this.showErrorPasswordConfirm();
        }

        if (isValidationSuccess)
            this.registrationRequest(informationOnCheck);
    }

    @Override
    public void verifyAndLoadAvatar(Context context, Uri uri) {
        if(mValidator.isValidAvatarSize(context, uri))
            mRegisterView.loadAvatar(uri);
        else
            mRegisterView.makeAvatarSizeToast();
    }

    @Override
<<<<<<< HEAD
    public void registrationRequest(InformationOnCheck informationOnCheck) {
=======
    public void onRegistrationSuccess() {
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c

    }
    @Override
    public void facebookLink() {

<<<<<<< HEAD
    }

    @Override
    public void onRegistrationSuccess() {

        mRegisterView.onRegistrationSuccess();
    }
=======
    @Override
    public void resume() {
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c

    @Override
    public void resume() {
    }

    @Override
    public void pause() {

    }

<<<<<<< HEAD
    @Override
    public void destroy() {
=======

    @Override public void destroy() {

        if (!mSubscription.isUnsubscribed() || mSubscription == null)
            mSubscription.unsubscribe();
>>>>>>> f2f1036637a61266c145f35c86c07cac350f944c
        if (mRegisterView != null)
            mRegisterView = null;
    }

}