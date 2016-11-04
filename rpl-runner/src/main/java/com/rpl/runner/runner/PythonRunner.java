package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PythonRunner extends Runner {

    private String stdout;

    private static final String SOLUTION_SOURCE_FILE = "solution.py";

    protected void generateFiles() {

        // Save solution
        PrintWriter out = null;
        try {
            out = new PrintWriter(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.print(super.solution);
        out.close();

    }

    protected void build() throws RunnerException {
        String[] args = {"python", "-m", "py_compile", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false);
        p1.start();
    }

    protected void run() throws RunnerException {
        String[] args = {"python", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, true);
        p1.start();

        super.stdout = p1.getStdout();
    }

}
