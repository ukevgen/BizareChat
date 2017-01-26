package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpRequestM;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.SignUpUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.signup.ResponseSignUpModel;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
import com.internship.pbt.bizarechat.presentation.model.InformationOnCheck;
import com.internship.pbt.bizarechat.presentation.model.RegistrationModel;
import com.internship.pbt.bizarechat.presentation.model.SignUpModel;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import java.io.File;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import rx.Subscriber;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private static final String PHONE_FORMAT = "+38 (0__) ___-__-__";
    private static final String USER_EXIST = "Sorry, this email already exist";
    private static final String AVATAR = "Select image to large";
    private final String TAG = "RegistrPresenterImpl";
    private Validator mValidator = new Validator();
    private RegistrationView mRegisterView;
    private File fileToUpload;
    private SignUpModel mRegistrationModel;
    private UseCase signUpUseCase;

    public RegistrationPresenterImpl() {
        super();
        mRegistrationModel = new RegistrationModel();
        mRegistrationModel.setPresenter(this);
    }

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
        if (mValidator.isThereSomeImage(view)) {

        }
    }

    @Override
    public void validateInformation(InformationOnCheck informationOnCheck) {

        /*this.hideErrorsInvalid();
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

        if (isValidationSuccess)*/
        this.registrationRequest(informationOnCheck);
    }

    @Override
    public void verifyAndLoadAvatar(Uri uri) {
        // mRegisterView.setPermission(uri);
        if (mValidator.isValidAvatarSize(mRegisterView.getContextActivity(), uri)) {
            fileToUpload = Converter.convertUriToFile(mRegisterView.getContextActivity(), uri);
            mRegisterView.loadAvatarToImageView(uri);
        } else {
            mRegisterView.makeToast(AVATAR);
        }
    }

    @Override
    public void registrationRequest(InformationOnCheck informationOnCheck) {

        SignUpUserM userM = new SignUpUserM();
        userM.setPhone("0630573927");
        userM.setWebsite("website");
        userM.setPassword("testpassword");
        userM.setEmail("teset4@mail.com");
        userM.setBlobId(null);
        userM.setFullName("myfullname");
        SignUpRequestM signUpRequestM = new SignUpRequestM();
        signUpRequestM.setUser(userM);

        Log.d(TAG, signUpRequestM.getUser().toString());

        signUpUseCase = new SignUpUseCase(new SessionDataRepository(), signUpRequestM);

        signUpUseCase.execute(new Subscriber<ResponseSignUpModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mRegisterView.makeToast(USER_EXIST);

            }

            @Override
            public void onNext(ResponseSignUpModel userSignUpResponce) {
                Log.d(TAG, "Logged with inf " + userSignUpResponce.getUser().toString());
            }
        });

    }


    @Override
    public void facebookLink(LoginResult loginResult) {
        Log.d("123", "Presenter Facebook request");
        mRegistrationModel.getFacebookLink(loginResult);

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

    @Override
    public void refreshLinkedInfInView(FacebookLinkInform linkInform) {
        Log.d("123", "callback " + linkInform.toString());

        mRegisterView.refreshInfAfterFacebookLink(linkInform);
    }
}