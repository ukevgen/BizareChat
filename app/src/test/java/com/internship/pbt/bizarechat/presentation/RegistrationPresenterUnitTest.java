package com.internship.pbt.bizarechat.presentation;

import android.content.Context;
import android.net.Uri;

import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.model.RegistrationModel;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Converter.class
})
public class RegistrationPresenterUnitTest {
    private String[] negativeTestPasswordLengthData = {"11111", "1111111111111", ""};
    private String[] positiveTestPasswordLengthData = {"111111", "1111111", "11111111111", "111111111111"};

    @Mock
    private RegistrationView mRegistrationFragment;

    @Mock
    private Context context;

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

    private RegistrationPresenter mRegistrationPresenter;

    private SignUpUserM SignUpUserM;

    @Before
    public void prepareData() {
        SignUpUserM = new SignUpUserM();
        SignUpUserM.setEmail("roman-kapshuk@ukr.net");
        SignUpUserM.setPassword("QA1we2");
        SignUpUserM.setPhone("0797878796");
        mRegistrationPresenter = new RegistrationPresenterImpl(
                registrationModel,
                contentRepository,
                sessionRepository);
        mRegistrationPresenter.setRegistrationView(mRegistrationFragment);

        PowerMockito.mockStatic(Converter.class);
        when(Converter.convertUriToFile(any(Context.class), any(Uri.class))).thenReturn(avatarFile);

        when(mRegistrationFragment.getContextActivity()).thenReturn(context);
    }

    @Test
    public void checkPasswordLengthNotMatches() {
        for (String value : negativeTestPasswordLengthData) {
            SignUpUserM.setPassword(value);
            mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");
            verify(mRegistrationFragment, atLeastOnce()).showErrorPasswordLength();
        }
    }

    @Test
    public void checkPasswordLengthMatches() {
        for (String value : positiveTestPasswordLengthData) {
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
    public void checkIfUserEnteredInvalidPassword() {
        SignUpUserM.setPassword("Invalid");
        mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
        verify(mRegistrationFragment, never()).showErrorInvalidPhone();
    }

    @Test
    public void checkIfUserEnteredInvalidPhone() {
        SignUpUserM.setPhone("Invalid");
        mRegistrationPresenter.validateInformation(SignUpUserM, "QA1we2");

        verify(mRegistrationFragment).showErrorInvalidPhone();
        verify(mRegistrationFragment, never()).showErrorInvalidPassword();
        verify(mRegistrationFragment, never()).showErrorInvalidEmail();
    }

    @Test
    public void checkAvatarSizeValidBehavior() {
        when(avatarFile.length()).thenReturn((long) (1024 * 1024 - 1));
        mRegistrationPresenter.verifyAndLoadAvatar(uri);

        verify(mRegistrationFragment).loadAvatarToImageView(uri);
    }

    @Test
    public void checkAvatarSizeInvalidBehavior() {
        long[] testData = {1024 * 1024, 1024 * 1024 + 1, 0};
        for (long value : testData) {
            when(avatarFile.length()).thenReturn(value);
            mRegistrationPresenter.verifyAndLoadAvatar(uri);

            verify(mRegistrationFragment, atMost(testData.length)).showError(anyString());
        }
    }

    @Test
    public void passwordMatch() {
    }

}
