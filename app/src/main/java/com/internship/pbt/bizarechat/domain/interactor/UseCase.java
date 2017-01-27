package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.SchedulersFactory;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private Subscription mSubscription = Subscriptions.empty();

    UseCase() {}

    protected abstract Observable<T> buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber useCaseSubscriber) {
        this.mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(SchedulersFactory.getThreadExecutor()))
                .observeOn(SchedulersFactory.getPostExecutor().getScheduler())
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }


}
