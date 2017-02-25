package com.internship.pbt.bizarechat.presentation.presenter.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpRequestM;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.domain.interactor.LoginUserUseCase;
import com.internship.pbt.bizarechat.domain.interactor.SignUpUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UploadFileUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.domain.model.signup.ResponseSignUpModel;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
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
    private static final String AVATAR = "Too large picture max size 1mb";
    private final String TAG = "RegistrPresenterImpl";
    private Validator mValidator;
    private RegistrationView mRegisterView;
    private File fileToUpload = null;
    private SignUpModel mRegistrationModel;
    private UseCase uploadFileUseCase;
    private UseCase signUpUseCase;
    private SignUpRequestM signUpRequestM;
    private UseCase loginUseCase;
    private CurrentUser currentUser;
    private CallbackManager callbackManager;
    private ContentRepository contentRepository;
    private SessionRepository sessionRepository;
    private Converter converter;


    public RegistrationPresenterImpl(RegistrationModel registrationModel,
                                     ContentRepository contentRepository,
                                     SessionRepository sessionRepository,
                                     Validator validator,
                                     Converter converter,
                                     CurrentUser currentUser) {
        super();
        this.mRegistrationModel = registrationModel;
        this.mRegistrationModel.setPresenter(this);
        this.contentRepository = contentRepository;
        this.sessionRepository = sessionRepository;
        this.mValidator = validator;
        this.converter = converter;
        this.currentUser = currentUser;
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
    public void uploadAvatar() {
        if (fileToUpload != null) {
            mRegisterView.showLoading();
            this.uploadFileUseCase = new UploadFileUseCase(contentRepository,
                    ApiConstants.CONTENT_TYPE_IMAGE_JPEG,
                    fileToUpload,
                    CurrentUser.CURRENT_AVATAR);
            uploadFileUseCase.execute(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    mRegisterView.showIsAvatarUploadedMessage();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (mRegisterView != null) {
                        String message = ErrorMessageFactory.
                                createMessageOnLogin(mRegisterView.getContextActivity(), e);
                        mRegisterView.hideLoading();
                        mRegisterView.showError("UploadAvatar " + message);
                    }
                }

                @Override
                public void onNext(Integer response) { // Now is return Integer blobId
                    currentUser.setAvatarBlobId(Long.valueOf(response));
                    onRegistrationSuccess();
                }
            });
        }
    }

    @Override
    public void validateInformation(SignUpUserM informationOnCheck, String passwordConf) {
        if (mRegisterView != null) mRegisterView.showLoading();
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
                passwordConf)) {
            isValidationSuccess = false;
            this.showErrorPasswordConfirm();
        }
        if (isValidationSuccess) {
            currentUser.setCurrentEmail(informationOnCheck.getEmail());
            currentUser.setCurrentPasswrod(informationOnCheck.getPassword());
            this.registrationRequest(informationOnCheck);
        } else {
            if (mRegisterView != null) mRegisterView.hideLoading();
        }
    }

    @Override
    public void verifyAndLoadAvatar(Uri uri) {
        // mRegisterView.setPermission(uri);
        fileToUpload = converter.compressPhoto(converter.convertUriToFile(uri));

        if (mValidator.isValidAvatarSize(fileToUpload)) {
            currentUser.setStringAvatar(converter.encodeAvatarTobase64(fileToUpload));
            loadAvatar();
        } else {
            showTooLargeImage();
            fileToUpload = null;
        }
    }

    private void loadAvatar() {
        mRegisterView.loadAvatarToImageView(fileToUpload);
    }

    private void showTooLargeImage() {
        mRegisterView.showTooLargeImage();
    }

    @Override
    public void verifyAndLoadAvatar(File file) {
        fileToUpload = file;
        if (mValidator.isValidAvatarSize(fileToUpload)) {
            mRegisterView.loadAvatarToImageView(fileToUpload);
        }
    }

    @Override
    public void registrationRequest(SignUpUserM userM) {
        userM.setPhone(mValidator.toApiPhoneFormat(userM.getPhone()));
        userM.setFacebookId(currentUser.getCurrentFacebookId());

        if (signUpRequestM == null)
            signUpRequestM = new SignUpRequestM();
        signUpRequestM.setUser(userM);

        signUpUseCase = new SignUpUseCase(sessionRepository, signUpRequestM);
        signUpUseCase.execute(new Subscriber<ResponseSignUpModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.toString());
                if (mRegisterView != null) {
                    mRegisterView.hideLoading();
                    mRegisterView.showError(ErrorMessageFactory.
                            createMessageOnRegistration(mRegisterView.getContextActivity(), e));
                }
            }

            @Override
            public void onNext(ResponseSignUpModel signUpModel) {
                Log.d(TAG, signUpModel.toString());
                currentUser.setCurrentUserId(Long.valueOf(signUpModel.getUser().getId()));
                authorize();
            }
        });

    }


    @Override
    public void facebookLink(LoginResult loginResult) {
        mRegistrationModel.getFacebookLink(loginResult);

    }

    @Override
    public void onRegistrationSuccess() {
        mRegisterView.hideLoading();
        currentUser.setAuthorized(true);

        //mRegisterView.goToMainActivity();
        mRegisterView.onRegistrationSuccess();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void stop() {
        if (uploadFileUseCase != null)
            uploadFileUseCase.unsubscribe();
        if (loginUseCase != null)
            loginUseCase.unsubscribe();
    }

    @Override
    public void destroy() {
        if (mRegisterView != null)
            mRegisterView = null;
    }

    private void authorize() {
        loginUseCase = new LoginUserUseCase(sessionRepository,
                new UserRequestModel(currentUser.getCurrentEmail(),
                        currentUser.getCurrentPassword()));

        loginUseCase.execute(new Subscriber<UserLoginResponse>() {
            @Override
            public void onCompleted() {
                if (fileToUpload != null)
                    uploadAvatar();
                else
                    onRegistrationSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (mRegisterView != null) {
                    mRegisterView.hideLoading();
                    mRegisterView.showError(ErrorMessageFactory.
                            createMessageOnRegistration(mRegisterView.getContextActivity(), e));
                }
            }

            @Override
            public void onNext(UserLoginResponse session) {
            }
        });
    }

    @Override
    public void logoutFacebookSdk() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void setCallbackToLoginFacebookButton() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Bundle param = new Bundle();
                param.putString("fields", "id, email");
                facebookLink(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public void setOnActivityResultInFacebookCallback(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshLinkedInfInView(FacebookLinkInform linkInform) {
        mRegisterView.showUserLinkedWithFacebook();
        currentUser.setFacebookToken(linkInform.getToken());
        currentUser.setCurrentFacebookId(linkInform.getUserId());
        mRegisterView.refreshInfAfterFacebookLink(linkInform);
    }
}