package com.internship.pbt.bizarechat.api;

import com.internship.pbt.bizarechat.data.SchedulersFactory;
import com.internship.pbt.bizarechat.data.executor.JobExecutorTest;
import com.internship.pbt.bizarechat.domain.executor.PostExecutorThread;
import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;
import com.internship.pbt.bizarechat.presentation.UiThreadTest;

import org.junit.rules.ExternalResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;


public class SynchronousExecution extends ExternalResource {
    @Override
    protected void before() throws Throwable {
        Field field = SchedulersFactory.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(SchedulersFactory.class, new SchedulersFactory.SchedulerProvider(){
            @Override
            public ThreadExecutor getThreadExecutor() {
                return new JobExecutorTest();
            }

            @Override
            public PostExecutorThread getPostExecutor() {
                return UiThreadTest.getInstance();
            }
        });
    }

    @Override
    protected void after() {
        try {
            Field field = SchedulersFactory.class.getDeclaredField("instance");
            field.setAccessible(true);
            Constructor constructor = SchedulersFactory.class.getDeclaredClasses()[0].getDeclaredConstructor();
            constructor.setAccessible(true);
            field.set(SchedulersFactory.class, constructor.newInstance());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
