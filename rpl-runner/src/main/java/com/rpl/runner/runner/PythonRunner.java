package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.LocalFileUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class PythonRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.py";
    private static final String TEST_SOURCE_FILE = "test.py";
    private static final String WRAPPER_SOURCE_PATH = "extras/test-wrappers/python/wrapper.py";
    private static final String WRAPPER_SOURCE_FILE = "wrapper.py";

    protected void generateFiles() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);

        if (super.mode.equals(TestMode.TEST)) {
            LocalFileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
            LocalFileUtils.copyFile(WRAPPER_SOURCE_PATH, Settings.EXECUTION_PATH + WRAPPER_SOURCE_FILE);
        }
    }

    protected void build() throws RunnerException {
        if (super.mode.equals(TestMode.INPUT)) {
            String[] args = {"python", "-m", "py_compile", SOLUTION_SOURCE_FILE};
            ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
            p1.start();
        } else { // TEST
            String[] args = {"python", "-m", "py_compile", WRAPPER_SOURCE_FILE};
            ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
            p1.start();
        }
    }

    protected void run() throws RunnerException {
        ProcessRunner p;
        if (super.mode.equals(TestMode.INPUT)) {
            String[] args = {"python", SOLUTION_SOURCE_FILE};
            p = new ProcessRunner(args, true, Runner.STAGE_RUN);
            p.setStdin(super.modeData);
            p.start();
        } else { // TEST
            String[] args = {"python", WRAPPER_SOURCE_FILE};
            p = new ProcessRunner(args, true, Runner.STAGE_RUN);
            p.start();
        }

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }

}
