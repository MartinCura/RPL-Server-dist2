package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.LocalFileUtils;

public class CRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.c";
    private static final String SOLUTION_OUT_FILE = "solution";

    protected void generateGenericFiles() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);
    }

    protected void generateFilesForTest() {
        // TODO
    }

    protected void buildForInput() throws RunnerException {
        String[] args = {"gcc", SOLUTION_SOURCE_FILE, "-o", "solution"};
        ProcessRunner p1 = new ProcessRunner(args, false, "build");
        p1.start();
    }

    protected void buildForTest() throws RunnerException {
        // TODO
    }

    protected void runForInput() throws RunnerException {
        String[] args = {"./" + SOLUTION_OUT_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true, "run");
        p1.start();

        super.stdout = p1.getStdout();
        super.stderr = p1.getStderr();
    }

    protected void runForTest() throws RunnerException {
        // TODO
    }
}
