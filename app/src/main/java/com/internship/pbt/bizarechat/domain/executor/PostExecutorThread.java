package com.internship.pbt.bizarechat.domain.executor;

import rx.Scheduler;

public interface PostExecutorThread {

    Scheduler getSheduler();
}