package com.rpl.runner.runner;

import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;

public abstract class Runner {

    public static final String STAGE_BUILD = "build";
    public static final String STAGE_RUN = "run";

    protected String stdout;
    protected String stderr;
    protected String tests;

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

    private void generateFiles() {
        generateGenericFiles();

        if (mode.equals(TestMode.TEST)) {
            generateFilesForTest();
        }
    }

    private void build() throws RunnerException {
        if (mode.equals(TestMode.INPUT)) {
            buildForInput();
        } else if (mode.equals(TestMode.TEST)) {
            buildForTest();
        }
    }

    private void run() throws RunnerException {
        if (mode.equals(TestMode.INPUT)) {
            runForInput();
        } else if (mode.equals(TestMode.TEST)) {
            runForTest();
        }
    }

    protected abstract void generateGenericFiles();
    protected abstract void generateFilesForTest();

    protected abstract void buildForInput() throws RunnerException;
    protected abstract void buildForTest() throws RunnerException;

    protected abstract void runForInput() throws RunnerException;
    protected abstract void runForTest() throws RunnerException;

    public String getStdout() {
        return stdout;
    }

    public String getTests() {
        return tests;
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
