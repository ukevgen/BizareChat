package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.util.Log;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import rx.subscriptions.CompositeSubscription;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private final String TAG = "RegistrPresenterImpl";
    private CompositeSubscription mSubscription;
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

  @Override public void showErrorInvalidEmail() {
        Log.d("123", "Presenter showErrorInvalidEmail");
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

 @Override public void validateInformation(ValidationInformation validationInformation) {

        boolean isSuccess = true;
        if (!mValidator.isValidEmail(validationInformation.getEmail())) {
            isSuccess = false;
            this.showErrorInvalidEmail();
        }
        if (!mValidator.isValidPassword(validationInformation.getPassword())) {
            isSuccess = false;
            this.showErrorInvalidPassword();
        }
        if (!mValidator.isValidPhoneNumber(validationInformation.getPhone())) {
            isSuccess = false;
            this.showErrorInvalidPhoneNumber();
        }
        if (!mValidator.isPasswordLengthMatches(validationInformation.getPassword())) {
            isSuccess = false;
            this.showErrorPasswordLength();
        }

        if (isSuccess)
            onRegistrationSuccess();
    }

    @Override
    public void onRegistrationSuccess() {

        //mRegisterView.onRegistrationSuccess(); TODO Make registration request
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

@Override public void destroy() {
        if (!mSubscription.isUnsubscribed() || mSubscription == null)
            mSubscription.unsubscribe();
        if (mRegisterView != null)
            mRegisterView = null;
    }

}