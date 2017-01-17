package com.internship.pbt.bizarechat.presentation;

import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;

import rx.Scheduler;

public class UiThread implements PostExecutorThread{

    @Override public Scheduler getSheduler() {
        return null; // TODO AnroidShedulers.mainThread();
    }
}
