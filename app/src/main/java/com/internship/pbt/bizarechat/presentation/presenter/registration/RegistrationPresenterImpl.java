package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.util.Log;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegisterView;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private final String TAG = "RegistrPresenterImpl";

    private Validator mValidator;
    private RegisterView mRegisterView;
    private ThreadExecutor mThreadExecutor;
    private PostExecutorThread mPostExecutorThread;


    @Override public void showErrorInvalidPassword() {
        mRegisterView.showErrorInvalidPassword();
    }

    @Override public void showErrorInvalidEmail() {
        mRegisterView.showErrorInvalidEmail();
    }

    @Override public void showErrorInvalidPhoneNumber() {
        mRegisterView.showErrorInvalidPhone();
    }

    @Override public void showViewLoading() {
        mRegisterView.showLoading();
    }

    @Override public void hideViewLoading() {
        mRegisterView.hideLoading();
    }

    @Override public void validateInformation(Observable<ValidationInformation> validationInformationObservable) {
        validationInformationObservable
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutorThread.getSheduler())
                .doOnRequest(a -> this.showViewLoading())
                .doOnUnsubscribe(() -> this.hideViewLoading())
                .subscribe(new ValidInformation());
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
