package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.exception.RunnerException;

public class JavaRunner extends Runner {

    private static final String SOLUTION_SOURCE_FILE = "Solution.java";
    private static final String SOLUTION_OUT_FILE = "Solution";

    protected void generateFiles() {
        // TODO
    }

    protected void build() throws RunnerException {
        String[] args = {"javac", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, "build");
        p1.start();
    }

    protected void run() throws RunnerException {
        String[] args = {"java", SOLUTION_OUT_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true, "run");
        p1.start();

        super.stdout = p1.getStdout();
    }

}
