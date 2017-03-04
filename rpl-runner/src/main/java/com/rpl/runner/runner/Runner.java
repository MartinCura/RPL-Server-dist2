package com.rpl.runner.runner;

import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;
import com.rpl.runner.utils.LocalFileUtils;

import java.util.Base64;
import java.util.List;

public abstract class Runner {

    public static final String STAGE_BUILD = "build";
    public static final String STAGE_RUN = "run";
    // -v Memory: 500 Mb; -f disk space: 10 Mb
    public static final String ULIMIT_COMMAND = "ulimit -v 500000; ulimit -f 10000;";

    protected String stdout;
    protected String stderr;
    protected String tests;

    public enum TestMode {
        TEST, INPUT;
    }

    protected String solution;
    protected TestMode mode = TestMode.INPUT;
    protected String modeData;

    protected List<String[]> extraFiles;

    public void process() throws RunnerException {
        generateFiles();
        build();
        run();
    }

    private void generateFiles() {
        generateGenericFiles();
        generateExtraFiles();

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

    public void generateExtraFiles() {
        if (extraFiles == null) {
            return;
        }
        for (String[] file : extraFiles) {
            LocalFileUtils.write(Settings.EXECUTION_PATH + file[0], Base64.getDecoder().decode(file[1]));
        }
    }

    public String getStdout() {
        return stdout;
    }

    public String getTests() {
        return tests;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setExtraFiles(List<String[]> extraFiles) {
        this.extraFiles = extraFiles;
    }

    public void setMode(TestMode mode) {
        this.mode = mode;
    }

    public void setModeData(String modeData) {
        this.modeData = modeData;
    }

}
