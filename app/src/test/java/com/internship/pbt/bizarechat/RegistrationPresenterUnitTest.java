package com.internship.pbt.bizarechat;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterUnitTest {

    @Mock
    RegistrationView mRegistrationFragment;

    private RegistrationPresenter mRegistrationPresenter;

    @Before
    public void prepareData() {
        mRegistrationPresenter = new RegistrationPresenterImpl();
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);
    }

    @Test
    public void checkIfUserEnteredInvalidEmail() {
        ValidationInformation validationInformation = new ValidationInformation();
        validationInformation.setEmail("Example@gmail.com");
        validationInformation.setPassword("QW12qwer");
        validationInformation.setPhone("380797878796");
        mRegistrationPresenter.validateInformation(validationInformation);

        verify(mRegistrationFragment).showErrorInvalidEmail();

    }


}
