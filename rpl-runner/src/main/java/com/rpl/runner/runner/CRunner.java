package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.LocalFileUtils;

public class CRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.c";
    private static final String SOLUTION_OUT_FILE = "solution";
    private static final String TEST_SOURCE_FILE = "test.c";
    private static final String TEST_OUT_FILE = "test";
    private static final String LIB_PATH = "../extras/runner-libs/c/";

    protected void generateGenericFiles() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);
    }

    protected void generateFilesForTest() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
    }

    protected void buildForInput() throws RunnerException {
        String[] args = {"gcc", SOLUTION_SOURCE_FILE, "-o", SOLUTION_OUT_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, "build");
        p1.start();
    }

    protected void buildForTest() throws RunnerException {
        String[] args = {"gcc", TEST_SOURCE_FILE, "-o", TEST_OUT_FILE, "-lcriterion"};
        ProcessRunner p1 = new ProcessRunner(args, false, "build");
        p1.start();
    }

    protected void runForInput() throws RunnerException {
        String[] args = {"./" + SOLUTION_OUT_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true, "run");
        p1.setStdin(super.modeData);
        p1.start();

        super.stdout = p1.getStdout();
        super.stderr = p1.getStderr();
    }

    protected void runForTest() throws RunnerException {
        String[] args = {"./" + TEST_OUT_FILE, "--custom-json"};
        ProcessRunner p1 = new ProcessRunner(args, true, "run");
        p1.setIgnoreStderr(true);
        p1.start();

        // yes, it is ok!
        super.stdout = p1.getStderr();
        super.stderr = p1.getStderr();
    }
}
