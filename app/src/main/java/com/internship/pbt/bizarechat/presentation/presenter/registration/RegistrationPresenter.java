package com.internship.pbt.bizarechat.presentation.presenter.registration;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;

import rx.Observable;

public interface RegistrationPresenter extends Presenter {

    void showErrorInvalidPassword();

    void showErrorInvalidEmail();

    void showErrorInvalidPhoneNumber();

    void hideErrorsInvalid();

    void validateInformation(Observable<ValidationInformation> validationInformationObservable);

    void saveUserAccInformation(ValidationInformation validationInformationObservable);

    void onRegistrationSuccess();

    void showErrorPasswordLength();

}