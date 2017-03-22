package com.internship.pbt.bizarechat.presentation;

import android.net.Uri;

import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.model.RegistrationModel;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import ru.tinkoff.decoro.watchers.FormatWatcher;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({
        CurrentUser.class,
})
public class RegistrationPresenterUnitTest {
    private static final String PHONE_FORMAT = "+38 (0__) ___-__-__";
    private String[] negativeTestPasswordLengthData = {"11111", "1111111111111", ""};
    private String[] positiveTestPasswordLengthData = {"11111111", "1111111", "11111111111", "111111111111"};
    @Mock
    private RegistrationView mRegistrationFragment;

    @Mock
    private Uri uri;

    @Mock
    private File avatarFile;

    @Mock
    private RegistrationModel registrationModel;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private Converter converter;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private Validator validator;

    private FormatWatcher formatWatcher;

    private RegistrationPresenter mRegistrationPresenter;

    private SignUpUserM signUpUserM;

    @Before
    public void prepareData() {
        PowerMockito.mockStatic(CurrentUser.class);
        when(CurrentUser.getInstance()).thenReturn(currentUser);
        signUpUserM = new SignUpUserM();
        signUpUserM.setEmail("roman-kapshuk@ukr.net");
        signUpUserM.setPassword("QA1we2");
        signUpUserM.setPhone("0797878796");
        mRegistrationPresenter = new RegistrationPresenterImpl(
                registrationModel,
                contentRepository,
                sessionRepository,
                validator,
                converter,
                currentUser);
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);

    }


    @Test
    public void checkPasswordLengthNotMatches() {
        for (String value : negativeTestPasswordLengthData) {
            signUpUserM.setPassword(value);
            mRegistrationPresenter.validateInformation(signUpUserM, "QA1we2");
            verify(mRegistrationFragment, atLeastOnce()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkPasswordLengthMatches() {
            when(validator.isPasswordLengthMatches(anyString())).thenReturn(false);
        mRegistrationPresenter.validateInformation(signUpUserM, "QA1we2");
            verify(mRegistrationFragment).showErrorPasswordLength();
    }

    @Test
    public void checkIfUserEnteredInvalidEmail() {
        when(validator.isValidEmail(anyString())).thenReturn(false);
        when(validator.isValidPhoneNumber(anyString())).thenReturn(true);
        when(validator.isValidPassword(anyString())).thenReturn(true);
        mRegistrationPresenter.validateInformation(signUpUserM, "");
        verify(mRegistrationFragment).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
    }

    @Test
    public void checkIfUserEnteredInvalidPassword() {
        when(validator.isValidEmail(anyString())).thenReturn(true);
        when(validator.isValidPhoneNumber(anyString())).thenReturn(true);
        when(validator.isValidPassword(anyString())).thenReturn(false);
        mRegistrationPresenter.validateInformation(signUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPhone() {
        when(validator.isValidEmail(anyString())).thenReturn(true);
        when(validator.isValidPhoneNumber(anyString())).thenReturn(false);
        when(validator.isValidPassword(anyString())).thenReturn(true);
        mRegistrationPresenter.validateInformation(signUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
    }

    @Test
    public void checkAvatarSizeValidBehavior() {
        when(validator.isValidAvatarSize(any(File.class))).thenReturn(true);
        mRegistrationPresenter.verifyAndLoadAvatar(any(Uri.class));
        verify(mRegistrationFragment, atLeastOnce()).loadAvatarToImageView(any(File.class));
    }

    @Test
    public void checkAvatarSizeInvalidBehavior() {
        when(validator.isValidAvatarSize(any(File.class))).thenReturn(false);
        mRegistrationPresenter.verifyAndLoadAvatar(uri);
        verify(mRegistrationFragment, atLeastOnce()).showTooLargeImage();
    }


    @Test
    public void checkPhoneMask() {
        assert formatWatcher.getMask().equals(PHONE_FORMAT);

    }


    @Test
    public void passwordMatch() {
    }

}