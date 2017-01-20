package com.internship.pbt.bizarechat;


import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterTest {
    @Mock
    private ValidationInformation validationInformation;

    @Mock
    private RegistrationFragment registrationView;

    private RegistrationPresenterImpl registrationPresenter;

    @Before
    public void prepareData(){
        registrationPresenter = new RegistrationPresenterImpl();
        registrationPresenter.setRegistrationView(registrationView);
    }

    @Test
    public void checkPasswordLengthNotMatches(){
        when(validationInformation.getPassword()).thenReturn(
                "aaaaaa",
                "aaaaaaa",
                "aaaaaaaaaaa",
                "aaaaaaaaaaaa");

        registrationPresenter.validateInformation(validationInformation);
    }
}
