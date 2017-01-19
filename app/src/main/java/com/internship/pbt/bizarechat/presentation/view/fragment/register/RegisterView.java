package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import com.internship.pbt.bizarechat.presentation.view.fragment.LoadDataView;

public interface RegisterView extends LoadDataView {

    void saveUser();

    void loginFacebook();

    void loginTwitter();

    String getEmailForValidation();

    String getPasswordForValidation();

    String getPhoneForValidation();
}
