package com.internship.pbt.bizarechat;

import android.content.Context;

import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
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

    @Mock
    private Context context;

    private RegistrationPresenter mRegistrationPresenter;

    private SignUpUserM SignUpUserM;

    @Before
    public void prepareData() {
        SignUpUserM = new SignUpUserM();
        SignUpUserM.setEmail("roman-kapshuk@ukr.net");
        SignUpUserM.setPassword("QA1we2");
        SignUpUserM.setPhone("0797878796");
        mRegistrationPresenter = new RegistrationPresenterImpl();
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);

    }

    @Test
    public void checkPasswordLengthNotMatches(){
        for(String value : negativeTestPasswordLengthData){
            SignUpUserM.setPassword(value);
            mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");
            verify(mRegistrationFragment, atLeastOnce()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkPasswordLengthMatches(){
        for(String value : positiveTestPasswordLengthData){
            SignUpUserM.setPassword(value);
            mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");
            verify(mRegistrationFragment, never()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkIfUserEnteredInvalidEmail() {
        SignUpUserM.setEmail("Invalid");
        mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPassword(){
        SignUpUserM.setPassword("Invalid");
        mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPhone(){
        SignUpUserM.setPhone("Invalid");
        mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
    }

    @Test
    public void passwordMatch(){}

}
