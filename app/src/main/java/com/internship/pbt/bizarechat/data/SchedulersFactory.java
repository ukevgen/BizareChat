package com.internship.pbt.bizarechat.data;


import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.presentation.UiThread;

public class SchedulersFactory {
    static SchedulerProvider instance = new DefaultSchedulerProvider();

    public static ThreadExecutor getThreadExecutor() {
        return instance.getThreadExecutor();
    }

    public static PostExecutorThread getPostExecutor() {
        return instance.getPostExecutor();
    }

    public interface SchedulerProvider {
        ThreadExecutor getThreadExecutor();

        PostExecutorThread getPostExecutor();
    }

    private static class DefaultSchedulerProvider implements SchedulerProvider {
        @Override
        public ThreadExecutor getThreadExecutor() {
            return JobExecutor.getInstance();
        }

        @Override
        public PostExecutorThread getPostExecutor() {
            return UiThread.getInstance();
        }
    }
}
