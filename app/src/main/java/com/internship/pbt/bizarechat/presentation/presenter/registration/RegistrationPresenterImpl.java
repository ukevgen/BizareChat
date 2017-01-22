package com.internship.pbt.bizarechat.presentation.presenter.registration;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
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
    public void hideErrorsInvalid() {
        mRegisterView.hideErrorInvalidEmail();
        mRegisterView.hideErrorInvalidPassword();
        mRegisterView.hideErrorInvalidPhone();
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
    public void validateInformation(ValidationInformation validationInformation) {

        this.hideErrorsInvalid();

        boolean isValidationSuccess = true;
        if (!mValidator.isValidEmail(validationInformation.getEmail())) {
            isValidationSuccess = false;
            this.showErrorInvalidEmail();
        }
        if (!mValidator.isValidPassword(validationInformation.getPassword())) {
            isValidationSuccess = false;
            this.showErrorInvalidPassword();
        }
        if (!mValidator.isValidPhoneNumber(validationInformation.getPhone())) {
            isValidationSuccess = false;
            this.showErrorInvalidPhoneNumber();
        }
        if (!mValidator.isPasswordLengthMatches(validationInformation.getPassword())) {
            isValidationSuccess = false;
            this.showErrorPasswordLength();
        }

        if (isValidationSuccess)
            this.registrationRequest(validationInformation);
    }


    @Override
    public void registrationRequest(ValidationInformation validationInformation) {

    }
    @Override
    public void facebookLink() {

    }

    @Override
    public void onRegistrationSuccess() {

        mRegisterView.onRegistrationSuccess();
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (mRegisterView != null)
            mRegisterView = null;
    }

}