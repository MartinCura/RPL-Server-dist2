package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.FileUtils;

public class JavaRunner extends Runner {

    private static final String HAMCREST_PATH = "../extras/runner-libs/java/hamcrest-core-1.3.jar";
    private static final String JUNIT_PATH = "../extras/runner-libs/java/junit-4.12.jar";

    private static final String SOLUTION_SOURCE_FILE = "Solution.java";
    private static final String TEST_SOURCE_FILE = "TestSolution.java";
    private static final String SOLUTION_OUT_FILE = "Solution";
    private static final String TEST_OUT_FILE = "TestSolution";


    protected void generateFiles() {
        FileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);

        if (super.mode.equals(TestMode.TEST)) {
            FileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
        }
    }

    protected void build() throws RunnerException {
        if (super.mode.equals(TestMode.INPUT)) {
            String[] args = {"javac", SOLUTION_SOURCE_FILE};
            ProcessRunner p1 = new ProcessRunner(args, false, "build");
            p1.start();
        } else { // TEST
            // javac -classpath ../../../runner-libs/java/junit-4.12.jar Solution.java TestSolution.java
            String[] args = {"javac", "-classpath", JUNIT_PATH, SOLUTION_SOURCE_FILE, TEST_SOURCE_FILE};
            ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
            p1.start();
        }
    }

    protected void run() throws RunnerException {
        ProcessRunner p = null;

        if (super.mode.equals(TestMode.INPUT)) {
            String[] args = {"java", SOLUTION_OUT_FILE};
            p = new ProcessRunner(args, true, "run");
            p.start();
        } else { // TEST
            //java -cp .:../../../runner-libs/java/ org.junit.runner.JUnitCore TestSolution
            String[] args = {"java", "-cp", ".:" + JUNIT_PATH + ":" + HAMCREST_PATH, "org.junit.runner.JUnitCore", TEST_OUT_FILE};
            p = new ProcessRunner(args, true, Runner.STAGE_RUN);
            p.start();
        }

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }

}
