package com.internship.pbt.bizarechat.domain.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;

import rx.Observable;

public interface NotificationsRepository {
    Observable<CreateSubscriptionResponse[]> createSubscription(String token);
}
