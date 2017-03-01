package com.internship.pbt.bizarechat.api;


import android.util.Log;

import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.data.datamodel.mappers.SessionModelMapper;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.requests.SessionRequest;
import com.internship.pbt.bizarechat.data.net.services.SessionService;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.data.util.HmacSha1Signature;
import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.exception.ErrorMessageFactory;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.login.LoginView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        UserToken.class,
        RetrofitApi.class,
        HmacSha1Signature.class,
        SessionModelMapper.class,
        ErrorMessageFactory.class})

public class SessionServiceUnitTest {
    private LoginPresenter presenter;
    private GetTokenUseCase getTokenUseCase;
    private SessionRepository sessionRepository;

    @Rule
    public SynchronousExecution synchronousExecution = new SynchronousExecution();

    @Mock
    private LoginView view;

    @Mock
    private Session session;

    @Mock
    private ResetPasswordUseCase resetPasswordUseCase;

    @Mock
    private SessionModel sessionModel;

    @Mock
    private SessionService sessionService;

    @Mock
    private UserToken userToken;

    @Mock
    private RetrofitApi retrofitApi;

    @Mock
    private Validator validator;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(RetrofitApi.class);
        when(RetrofitApi.getRetrofitApi()).thenReturn(retrofitApi);
        PowerMockito.mockStatic(HmacSha1Signature.class);
        when(HmacSha1Signature.calculateSignature(anyInt(), anyLong())).thenReturn("");
        PowerMockito.mockStatic(SessionModelMapper.class);
        when(SessionModelMapper.transform(any(SessionModel.class))).thenReturn(session);
        PowerMockito.mock(Log.class);

        when(retrofitApi.getSessionService()).thenReturn(sessionService);
        when(session.getToken()).thenReturn("");

        sessionRepository = spy(new SessionDataRepository(BizareChatApp.getInstance().getSessionService()));
        getTokenUseCase = spy(new GetTokenUseCase(
                sessionRepository));
        presenter = new LoginPresenterImpl(resetPasswordUseCase, sessionRepository, validator, CurrentUser.getInstance());
        presenter.setLoginView(view);
    }

    @Test
    public void checkSuccessSessionRequestBehavior() throws Exception{
        PowerMockito.mockStatic(UserToken.class);
        when(UserToken.getInstance()).thenReturn(userToken);

        when(sessionService
                .getSession(any(SessionRequest.class)))
                .thenReturn(Observable.just(sessionModel));

        doNothing().when(userToken).saveToken(anyString());

//        presenter.requestSession();

        verify(getTokenUseCase).execute(any(Subscriber.class));
        verify(sessionRepository).getSession();
        verify(sessionService).getSession(any(SessionRequest.class));
        verify(userToken).saveToken(anyString());
    }

    @Test
    public void checkFailureSessionRequestBehavior(){
        PowerMockito.mockStatic(ErrorMessageFactory.class);
        when(ErrorMessageFactory.createMessageOnLogin(any(), any())).thenReturn("");

        when(sessionService
                .getSession(any(SessionRequest.class)))
                .thenReturn(Observable.error(new HttpException(
                        Response.error(422, ResponseBody.create(null, new byte[0])))
                ));

//        presenter.requestSession();

        verify(getTokenUseCase).execute(any(Subscriber.class));
        verify(sessionRepository).getSession();
        verify(sessionService).getSession(any(SessionRequest.class));
        verify(view).showError(anyString());
    }
}
