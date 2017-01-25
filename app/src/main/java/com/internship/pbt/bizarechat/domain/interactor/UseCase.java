package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.presentation.UiThread;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private ThreadExecutor mThreadExecutor;
    private PostExecutorThread mPostExecutorThread;
    private Subscription mSubscription = Subscriptions.empty();

    protected UseCase() {
        this.mThreadExecutor = JobExecutor.getInstance();
        this.mPostExecutorThread = UiThread.getInstance();
    }

    protected abstract Observable<T> buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber useCaseSubscriber) {
        this.mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutorThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }


}
