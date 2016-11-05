package com.rpl.runner.runner;

import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;

public abstract class Runner {

    public static final String STAGE_BUILD = "build";
    public static final String STAGE_RUN = "run";

    protected String stdout;
    protected String stderr;

    protected String solution;

    public void process(String solution) throws RunnerException {
        this.solution = solution;

        generateFiles();
        build();
        run();
    }

    protected abstract void generateFiles();
    protected abstract void build() throws RunnerException;
    protected abstract void run() throws RunnerException;

    public String getStdout() {
        return stdout;
    }
}
