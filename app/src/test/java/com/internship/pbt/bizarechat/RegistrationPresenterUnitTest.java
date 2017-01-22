package com.internship.pbt.bizarechat;

import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterUnitTest {
    private String[] positiveTestPasswordLengthData = {"11111", "1111111111111", ""};
    private String[] negativeTestPasswordLengthData = {"111111", "1111111", "11111111111", "111111111111"};

    @Mock
    private ValidationInformation validationInformation;

    @Mock
    private RegistrationView mRegistrationFragment;

    private RegistrationPresenter mRegistrationPresenter;

    @Before
    public void prepareData(){
        mRegistrationPresenter = new RegistrationPresenterImpl();
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);
    }

    @Test
    public void checkPasswordLengthNotMatches(){
        when(validationInformation.getEmail()).thenReturn("email");
        when(validationInformation.getPhone()).thenReturn("phone");

        for(String value : positiveTestPasswordLengthData){
            when(validationInformation.getPassword()).thenReturn(value);
            mRegistrationPresenter.validateInformation(validationInformation);
            verify(mRegistrationFragment).showErrorPasswordLength();
        }
    }

    @Test
    public void checkPasswordLengthMatches() {
        when(validationInformation.getEmail()).thenReturn("email");
        when(validationInformation.getPhone()).thenReturn("phone");

        for (String value : negativeTestPasswordLengthData) {
            when(validationInformation.getPassword()).thenReturn(value);
            mRegistrationPresenter.validateInformation(validationInformation);
            verify(mRegistrationFragment, calls(0)).showErrorPasswordLength();
        }
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

    @Test
    public void passwordMatch(){}


}
