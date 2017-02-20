package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;
import com.internship.pbt.bizarechat.domain.repository.NotificationsRepository;

import rx.Observable;


public class CreateSubscriptionUseCase extends UseCase<CreateSubscriptionResponse[]> {
    private NotificationsRepository repository;
    private String firebaseToken;

    public CreateSubscriptionUseCase(NotificationsRepository dialogsRepository) {
        this.repository = dialogsRepository;
    }

    @Override
    protected Observable<CreateSubscriptionResponse[]> buildUseCaseObservable() {
        return repository.createSubscription(firebaseToken);
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
