package com.rpl.runner.runner;

import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;

public abstract class Runner {

    public static final String STAGE_BUILD = "build";
    public static final String STAGE_RUN = "run";

    protected String stdout;
    protected String stderr;

    public enum TestMode {
        TEST, INPUT;
    }

    protected String solution;
    protected TestMode mode = TestMode.INPUT;
    protected String modeData;

    public void process() throws RunnerException {
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

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setMode(TestMode mode) {
        this.mode = mode;
    }

    public void setModeData(String modeData) {
        this.modeData = modeData;
    }
}
