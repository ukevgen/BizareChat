package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import android.net.Uri;

import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

public interface RegistrationView extends LoadDataView {

    void loginFacebook();

    void showErrorInvalidEmail();

    void showErrorInvalidPassword();

    void showErrorInvalidPhone();

    void hideErrorInvalidEmail();

    void hideErrorInvalidPassword();

    void hideErrorInvalidPhone();

    void getInformationForValidation();

    void onRegistrationSuccess();

    void showErrorPasswordLength();

    void setAnimation();

    void startOnFacebookLinkSuccessAnim();

    void startOnFailedFacebooLinkkAnim();

    void startSuccessSignUpAnim();

    void startFailedSignUpAnim();

    void showPictureChooser();

    void loadAvatar(Uri uri);

    void makeAvatarSizeToast();

    void showErrorPasswordConfirm();

    void hideErrorPasswordConfirm();

    void setPermission(Uri uri);
}
