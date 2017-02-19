package com.internship.pbt.bizarechat.domain.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponseWrapper;

import rx.Observable;

public interface NotificationsRepository {
    Observable<CreateSubscriptionResponseWrapper> createSubscription(String token);
}
