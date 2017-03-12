package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.CreateSubscriptionRequest;
import com.internship.pbt.bizarechat.data.net.services.NotificationService;
import com.internship.pbt.bizarechat.data.util.HmacSha1Signature;
import com.internship.pbt.bizarechat.domain.repository.NotificationsRepository;

import rx.Observable;

public class PushNotificationsRepository implements NotificationsRepository {
    private NotificationService service;

    public PushNotificationsRepository(NotificationService service) {
        this.service = service;
    }

    public Observable<CreateSubscriptionResponse[]> createSubscription(String token) {
        int nonce = (int) (Math.random() * 1000);
        long timestamp = System.currentTimeMillis();
        String udid = HmacSha1Signature.calculateSignature(nonce, timestamp);
        CreateSubscriptionRequest.Device device = new CreateSubscriptionRequest.Device();
        device.setPlatform(ApiConstants.PLATFORM);
        device.setUdid(udid);

        CreateSubscriptionRequest.PushToken pushToken = new CreateSubscriptionRequest.PushToken();
        pushToken.setEnvironment(ApiConstants.ENVIRONMENT);
        pushToken.setClientIdSequence(token);

        CreateSubscriptionRequest request = new CreateSubscriptionRequest();
        request.setDevice(device);
        request.setNotificationChannels(ApiConstants.NOTIFICATION_CHANNELS);
        request.setPushToken(pushToken);

        return service.createSubscription(UserToken.getInstance().getToken(), request);
    }
}
