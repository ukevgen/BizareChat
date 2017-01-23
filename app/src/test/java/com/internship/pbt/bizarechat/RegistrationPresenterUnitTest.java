package com.internship.pbt.bizarechat;

import com.internship.pbt.bizarechat.presentation.model.InformationOnCheck;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterUnitTest {
    private String[] negativeTestPasswordLengthData = {"11111", "1111111111111", ""};
    private String[] positiveTestPasswordLengthData = {"111111", "1111111", "11111111111", "111111111111"};

    @Mock
    private RegistrationView mRegistrationFragment;

    private RegistrationPresenter mRegistrationPresenter;

    private InformationOnCheck mInformationOnCheck;

    @Before
    public void prepareData() {
        mInformationOnCheck = new InformationOnCheck();
        mInformationOnCheck.setEmail("Example@gmail.com");
        mInformationOnCheck.setPassword("QA1we2");
        mInformationOnCheck.setPhone("0797878796");
        mRegistrationPresenter = new RegistrationPresenterImpl();
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);

    }

    @Test
    public void checkPasswordLengthNotMatches(){
        for(String value : negativeTestPasswordLengthData){
            mInformationOnCheck.setPassword(value);
            mRegistrationPresenter.validateInformation(mInformationOnCheck);
            verify(mRegistrationFragment, atLeastOnce()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkPasswordLengthMatches(){
        for(String value : positiveTestPasswordLengthData){
            mInformationOnCheck.setPassword(value);
            mRegistrationPresenter.validateInformation(mInformationOnCheck);
            verify(mRegistrationFragment, never()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkIfUserEnteredInvalidEmail() {
        mInformationOnCheck.setEmail("Invalid");
        mRegistrationPresenter.validateInformation(mInformationOnCheck);

        verify(mRegistrationFragment).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPassword(){
        mInformationOnCheck.setPassword("Invalid");
        mRegistrationPresenter.validateInformation(mInformationOnCheck);

        verify(mRegistrationFragment).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPhone(){
        mInformationOnCheck.setPhone("Invalid");
        mRegistrationPresenter.validateInformation(mInformationOnCheck);

        verify(mRegistrationFragment).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
    }

    @Test
    public void passwordMatch(){}



}
