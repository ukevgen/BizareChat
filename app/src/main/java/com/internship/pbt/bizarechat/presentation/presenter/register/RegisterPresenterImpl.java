package com.internship.pbt.bizarechat.presentation.presenter.register;

import android.util.Log;

import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.util.Validator;

import rx.Observable;
import rx.Subscriber;

public class RegisterPresenterImpl implements RegistrationPresenter {

    private final String TAG = "RegisterPresenterImpl";

    private Validator mValidator;
    private UseCase mValidateUseCase;


    @Override public void showErrorInvalidPassword() {

    }

    @Override public void showErrorInvalidEmail() {

    }

    @Override public void showErrorInvalidPhoneNumber() {

    }

    @Override public void validateInformation(Observable<ValidationInformation> validationInformationObservable) {
        mValidateUseCase.execute(new ValidInformation());
    }

    @Override public void saveUserAccInformation(ValidationInformation validationInformation) {

    }

    @Override public void resume() {

    }

    @Override public void pause() {

    }

    @Override public void destroy() {

    }


    private final class ValidInformation extends Subscriber<ValidationInformation> {

        @Override public void onCompleted() {
            Log.d(TAG, this.getClass().getSimpleName() +
                    " Validation of income information completed");
        }

        @Override public void onError(Throwable e) {
            Log.d(TAG, this.getClass().getSimpleName() +
                    " Validation of income information FAIL");
        }

        @Override public void onNext(ValidationInformation validationInformation) {

            if (!mValidator.isValidEmail(validationInformation.getMail()))
                showErrorInvalidEmail();
            if (!mValidator.isValidPassword(validationInformation.getPassword()))
                showErrorInvalidPassword();
            if (!mValidator.isValidPhoneNumber(validationInformation.getPhone()))
                showErrorInvalidPhoneNumber();

            if (mValidator.isValidEmail(validationInformation.getMail()) &
                    mValidator.isValidPassword(validationInformation.getPassword()) &
                    mValidator.isValidPhoneNumber(validationInformation.getPhone()))
                saveUserAccInformation(validationInformation);
        }
    }

}
