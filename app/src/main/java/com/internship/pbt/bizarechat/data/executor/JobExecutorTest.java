package com.internship.pbt.bizarechat.data.executor;

import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;

public class JobExecutorTest implements ThreadExecutor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
