package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.internship.pbt.bizarechat.presentation.model.InformationOnCheck;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private static final String PHONE_FORMAT = "+38 (0__) ___-__-__";
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
    public void showErrorPasswordConfirm() {
        mRegisterView.showErrorPasswordConfirm();
    }

    @Override
    public void createFormatWatcher() {
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_FORMAT);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        mRegisterView.addPhoneNumberFormatting(formatWatcher);
    }

    @Override
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
    public void loadAvatar(ImageView view) {
        if(mValidator.isThereSomeImage(view)){
            
        }
    }

    @Override
    public void validateInformation(InformationOnCheck informationOnCheck) {

        this.hideErrorsInvalid();
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
        if (!mValidator.isPasswordMatch(informationOnCheck.getPassword(),
                informationOnCheck.getPasswordConf())) {
            isValidationSuccess = false;
            this.showErrorPasswordConfirm();
        }

        if (isValidationSuccess)
            this.registrationRequest(informationOnCheck);
    }
    
    @Override
    public void verifyAndLoadAvatar(Context context, Uri uri) {
        // mRegisterView.setPermission(uri);
        if (mValidator.isValidAvatarSize(context, uri)) {
            mRegisterView.loadAvatarToImageView(uri);
        } else {
            mRegisterView.makeAvatarSizeToast();
        }
    }

    @Override
    public void registrationRequest(InformationOnCheck informationOnCheck) {
    }


    @Override
    public void facebookLink() {

    }

    @Override
    public void onRegistrationSuccess() {
        mRegisterView.onRegistrationSuccess();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
        if (mRegisterView != null)
            mRegisterView = null;
    }

}