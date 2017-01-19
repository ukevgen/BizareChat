package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import com.internship.pbt.bizarechat.presentation.view.LoadDataView;

public interface RegisterView extends LoadDataView {

    void saveUser();

    void loginFacebook();

    void loginTwitter();
}
