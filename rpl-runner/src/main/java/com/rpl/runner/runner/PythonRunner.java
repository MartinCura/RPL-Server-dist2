package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.FileWriter;

public class PythonRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.py";

    protected void generateFiles() {
        FileWriter.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);
    }

    protected void build() throws RunnerException {
        String[] args = {"python", "-m", "py_compile", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
        p1.start();
    }

    protected void run() throws RunnerException {
        String[] args = {"python", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true, Runner.STAGE_RUN);
        p1.start();

        super.stdout = p1.getStdout();
        super.stderr = p1.getStderr();
    }

}
