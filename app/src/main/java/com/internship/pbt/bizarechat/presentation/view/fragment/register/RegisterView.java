package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

public interface RegisterView extends LoadDataView {

    void loginFacebook();

    void showErrorInvalidEmail();

    void showErrorInvalidPassword();

    void showErrorInvalidPhone();

    void hideErrorInvalidEmail();

    void hideErrorInvalidPassword();

    void hideErrorInvalidPhone();

    void getInformationForValidation();
}
