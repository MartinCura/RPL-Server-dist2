package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.LocalFileUtils;

public class PythonRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.py";
    private static final String TEST_SOURCE_FILE = "test.py";
    private static final String WRAPPER_SOURCE_PATH = "extras/test-wrappers/python/wrapper.py";
    private static final String WRAPPER_SOURCE_FILE = "wrapper.py";

    protected void generateGenericFiles() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);
    }

    protected void generateFilesForTest() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
        LocalFileUtils.copyFile(WRAPPER_SOURCE_PATH, Settings.EXECUTION_PATH + WRAPPER_SOURCE_FILE);
    }

    protected void buildForInput() throws RunnerException {
        String[] args = {"python", "-m", "py_compile", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
        p1.start();
    }

    protected void buildForTest() throws RunnerException {
        String[] args = {"python", "-m", "py_compile", WRAPPER_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
        p1.start();
    }

    protected void runForInput() throws RunnerException {
        String[] args = {"sh", "-c", ULIMIT_COMMAND + " python " + SOLUTION_SOURCE_FILE};
        ProcessRunner p = new ProcessRunner(args, true, Runner.STAGE_RUN);
        p.setStdin(super.modeData);
        p.start();

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }

    protected void runForTest() throws RunnerException {
        String[] args = {"sh", "-c", ULIMIT_COMMAND + " python " + WRAPPER_SOURCE_FILE};
        ProcessRunner p = new ProcessRunner(args, true, Runner.STAGE_RUN);
        p.start();

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }
}
