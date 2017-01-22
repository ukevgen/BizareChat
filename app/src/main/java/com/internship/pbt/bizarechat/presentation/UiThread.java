package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

public class UiThread implements PostExecutorThread {

    private static UiThread mUiThread;
    private UiThread() {

    }

    public static UiThread getInstance(){
        if(mUiThread == null)
            mUiThread = new UiThread();

        return mUiThread;
    }

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
