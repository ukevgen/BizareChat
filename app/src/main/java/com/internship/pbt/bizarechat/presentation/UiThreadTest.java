package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;

import rx.Scheduler;
import rx.schedulers.Schedulers;


public class UiThreadTest implements PostExecutorThread {
    private static UiThreadTest mUiThread;

    private UiThreadTest() {

    }

    public static UiThreadTest getInstance() {
        if (mUiThread == null)
            mUiThread = new UiThreadTest();

        return mUiThread;
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.immediate();
    }
}
