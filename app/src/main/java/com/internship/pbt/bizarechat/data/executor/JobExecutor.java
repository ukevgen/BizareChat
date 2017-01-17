package com.internship.pbt.bizarechat.data.executor;

import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JobExecutor implements ThreadExecutor {

    private static volatile JobExecutor sInstance;

    private final ThreadPoolExecutor mThreadPoolExecutor;

    public static JobExecutor getInstance(){
        JobExecutor local = sInstance;
        if(local == null) {
            synchronized (JobExecutor.class) {
                local = sInstance;
                if(local == null){
                    sInstance = local = new JobExecutor();
                }
            }
        }
        return local;
    }

    private JobExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                40,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new JobThreadFactory());
    }

    @Override public void execute(Runnable command) {
        mThreadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory{

        private int counter = 0;

        @Override public Thread newThread(Runnable r) {
            return new Thread(r, "BizareChatThread" + counter++);
        }
    }
}