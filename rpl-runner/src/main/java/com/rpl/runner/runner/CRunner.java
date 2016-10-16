package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.exception.RunnerException;

public class CRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "solution.c";
    private static final String SOLUTION_OUT_FILE = "solution";

    protected void build() throws RunnerException {
        // -x c for compiling a file without the c suffix
        String[] args = {"gcc", SOLUTION_SOURCE_FILE, "-o", "solution"};
        ProcessRunner p1 = new ProcessRunner(args, false);
        p1.start();
    }

    protected void run() throws RunnerException {
        String[] args = {"./" + SOLUTION_OUT_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true);
        p1.start();

        super.stdout = p1.getStdout();
    }
}
