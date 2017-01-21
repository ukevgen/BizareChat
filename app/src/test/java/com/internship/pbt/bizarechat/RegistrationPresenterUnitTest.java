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

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterUnitTest {

    @Mock
    private RegistrationView mRegistrationFragment;

    private RegistrationPresenter mRegistrationPresenter;

    private ValidationInformation mValidationInformation;
    @Before
    public void prepareData() {
        mValidationInformation = new ValidationInformation();
        mValidationInformation.setEmail("Example@gmail.com");
        mValidationInformation.setPassword("QA1we2");
        mValidationInformation.setPhone("0797878796");
        mRegistrationPresenter = new RegistrationPresenterImpl();
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);
    }

    @Test
    public void checkIfUserEnteredInvalidEmail() {
        mValidationInformation.setEmail("Invalid");
        mRegistrationPresenter.validateInformation(mValidationInformation);

        verify(mRegistrationFragment).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPassword(){
        mValidationInformation.setPassword("Invalid");
        mRegistrationPresenter.validateInformation(mValidationInformation);

        verify(mRegistrationFragment).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPhone(){
        mValidationInformation.setPhone("Invalid");
        mRegistrationPresenter.validateInformation(mValidationInformation);

        verify(mRegistrationFragment).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
    }




}
