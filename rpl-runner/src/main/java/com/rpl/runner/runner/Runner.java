package com.rpl.runner.runner;

import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;

public abstract class Runner {
    protected String stdout;

    public void process() throws RunnerException {
        build();
        run();
    }

    protected abstract void build() throws RunnerException;
    protected abstract void run() throws RunnerException;

    public String getStdout() {
        return stdout;
    }
}
