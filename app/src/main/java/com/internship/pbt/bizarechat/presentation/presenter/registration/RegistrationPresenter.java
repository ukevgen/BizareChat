package com.internship.pbt.bizarechat.presentation.presenter.registration;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

public interface RegistrationPresenter extends Presenter {

    void showErrorInvalidPassword();

    void setRegistrationView(RegistrationView registerView);

    void showErrorInvalidEmail();

    void showErrorInvalidPhoneNumber();

    void hideErrorsInvalid();

    void validateInformation(ValidationInformation validationInformation);

    void onRegistrationSuccess();

    void facebookLink();

    void registrationRequest(ValidationInformation validationInformation);

    void showErrorPasswordLength();

}