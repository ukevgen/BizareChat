package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;
import rx.schedulers.Schedulers;

public abstract class UseCase {

    private ThreadExecutor mThreadExecutor;
    private PostExecutorThread mPostExecutorThread;
    private Subscription mSubscription = Subscriptions.empty();

    protected UseCase(ThreadExecutor threadExecutor,
                      PostExecutorThread postExecutionThread) {
        this.mThreadExecutor = threadExecutor;
        this.mPostExecutorThread = postExecutionThread;
    }
    protected abstract Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber useCaseSubscriber) {
        this.mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutorThread.getSheduler())
                .subscribe(useCaseSubscriber);
    }



}
