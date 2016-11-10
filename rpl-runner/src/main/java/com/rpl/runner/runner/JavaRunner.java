package com.rpl.runner.runner;

import com.rpl.runner.ProcessRunner;
import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.utils.LocalFileUtils;

public class JavaRunner extends Runner {

    private static final String HAMCREST_PATH = "../extras/runner-libs/java/hamcrest-core-1.3.jar";
    private static final String JUNIT_PATH = "../extras/runner-libs/java/junit-4.12.jar";

    private static final String SOLUTION_SOURCE_FILE = "Solution.java";
    private static final String TEST_SOURCE_FILE = "TestSolution.java";
    private static final String SOLUTION_OUT_FILE = "Solution";

    private static final String WRAPPER_SOURCE_PATH = "extras/test-wrappers/java/Wrapper.java";
    private static final String WRAPPER_SOURCE_FILE = "Wrapper.java";
    private static final String WRAPPER_OUT_FILE = "Wrapper";


    protected void generateGenericFiles() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + SOLUTION_SOURCE_FILE, super.solution);
    }

    protected void generateFilesForTest() {
        LocalFileUtils.write(Settings.EXECUTION_PATH + TEST_SOURCE_FILE, super.modeData);
        LocalFileUtils.copyFile(WRAPPER_SOURCE_PATH, Settings.EXECUTION_PATH + WRAPPER_SOURCE_FILE);
    }

    protected void buildForInput() throws RunnerException {
        String[] args = {"javac", SOLUTION_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, "build");
        p1.start();
    }

    protected void buildForTest() throws RunnerException {
        // javac -classpath ../../../runner-libs/java/junit-4.12.jar Solution.java TestSolution.java Wrapper.java
        String[] args = {"javac", "-classpath", JUNIT_PATH, SOLUTION_SOURCE_FILE, TEST_SOURCE_FILE, WRAPPER_SOURCE_FILE};
        ProcessRunner p1 = new ProcessRunner(args, false, Runner.STAGE_BUILD);
        p1.start();
    }

    protected void runForInput() throws RunnerException {
        String[] args = {"java", SOLUTION_OUT_FILE};
        ProcessRunner p = new ProcessRunner(args, true, "run");
        p.start();

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }

    protected void runForTest() throws RunnerException {
        //java -cp .:../../../runner-libs/java/ org.junit.runner.JUnitCore TestSolution
        String[] args = {"java", "-cp", ".:" + JUNIT_PATH + ":" + HAMCREST_PATH, WRAPPER_OUT_FILE};
        ProcessRunner p = new ProcessRunner(args, true, Runner.STAGE_RUN);
        p.start();

        super.stdout = p.getStdout();
        super.stderr = p.getStderr();
    }
}
