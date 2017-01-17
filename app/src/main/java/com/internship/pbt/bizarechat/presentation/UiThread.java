package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

public class UiThread implements PostExecutorThread{

    @Override public Scheduler getSheduler() {
        return AndroidSchedulers.mainThread();
    }
}
