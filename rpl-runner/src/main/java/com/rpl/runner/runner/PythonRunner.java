package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.FileUtils;

public class PythonRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.py";
    private static final String TEST_SOURCE_FILE = "test.py";

    protected void generateFiles() {
        FileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);

        if (super.mode.equals(TestMode.TEST)) {
            FileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
        }
    }

    protected void build() throws RunnerException {
        if (super.mode.equals(TestMode.INPUT)) {
            String[] args = {"python", "-m", "py_compile", SOLUTION_SOURCE_FILE};
            ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
            p1.start();
        } else { // TEST
            String[] args = {"python", "-m", "py_compile", TEST_SOURCE_FILE};
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
            String[] args = {"python", TEST_SOURCE_FILE};
            p = new ProcessRunner(args, true, Runner.STAGE_RUN);
            p.start();
        }

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }

}
