package com.internship.pbt.bizarechat.service.gcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponseWrapper;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.repository.PushNotificationsRepository;
import com.internship.pbt.bizarechat.domain.events.FirebaseTokenRefreshCompleteEvent;
import com.internship.pbt.bizarechat.domain.interactor.CreateSubscriptionUseCase;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;


public class BizareChatFirebaseIdService extends FirebaseInstanceIdService {

    private static final String TAG = BizareChatFirebaseIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: " + token);

        CreateSubscriptionUseCase useCase = new CreateSubscriptionUseCase(
                new PushNotificationsRepository(RetrofitApi.getRetrofitApi().getNotificationService()));
        useCase.setFirebaseToken(token);

        useCase.execute(new Subscriber<CreateSubscriptionResponseWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(CreateSubscriptionResponseWrapper response) {
                EventBus.getDefault().post(new FirebaseTokenRefreshCompleteEvent(token));
            }
        });
    }

    private void storeRegIdInPref(String token) {
        CurrentUser.getInstance().setFirebaseToken(token);
    }
}
