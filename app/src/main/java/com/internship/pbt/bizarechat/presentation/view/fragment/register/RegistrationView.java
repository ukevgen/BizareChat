package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import android.content.Context;
import android.net.Uri;

import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

import ru.tinkoff.decoro.watchers.FormatWatcher;

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

    Context getContextActivity();

    void startOnFacebookLinkSuccessAnim();

    void startOnFailedFacebooLinkkAnim();

    void startSuccessSignUpAnim();

    void startFailedSignUpAnim();

    void showPictureChooser();

    void loadAvatar(Uri uri);

    void makeAvatarSizeToast();

    void showErrorPasswordConfirm();

    void hideErrorPasswordConfirm();

    void addPhoneNumberFormatting(FormatWatcher formatWatcher);

}
