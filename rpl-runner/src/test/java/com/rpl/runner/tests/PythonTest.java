package com.rpl.runner.tests;

import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.PythonRunner;
import com.rpl.runner.runner.Runner;
import com.rpl.runner.utils.DirectoryCleaner;
import junit.framework.TestCase;

public class PythonTest extends TestCase {

    public void testBuildAndRunOk() {
        DirectoryCleaner.clean();

        String solution = "print 'Hello World'";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.INPUT);
        runner.setModeData("");
        boolean testOk = true;
        try {
            runner.process();
        } catch (RunnerException e) {
            testOk = false;
        }

        assertTrue(testOk);
    }

    public void testBuildNotOk() {
        DirectoryCleaner.clean();

        String solution = "print Hello World'";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.INPUT);
        runner.setModeData("");
        boolean testOk = false;
        try {
            runner.process();
        } catch (RunnerException e) {
            if ((e.getType().equals(RunnerException.TYPE_STAGE)) && (e.getStage().equals(Runner.STAGE_BUILD))) {
                testOk = true;
            }
        }

        assertTrue(testOk);
    }

    public void testRunNotOk() {
        DirectoryCleaner.clean();

        String solution = "raise Exception('error on runtime!')";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.INPUT);
        runner.setModeData("");
        boolean testOk = false;
        try {
            runner.process();
        } catch (RunnerException e) {
            if ((e.getType().equals(RunnerException.TYPE_STAGE)) && (e.getStage().equals(Runner.STAGE_RUN))) {
                testOk = true;
            }
        }

        assertTrue(testOk);
    }

    public void testRunWithTimeout() {
        DirectoryCleaner.clean();
        // Avoid waiting 10s
        Settings.EXECUTION_TIMEOUT = 1000;

        String solution = "while(True):\n" +
                          "\tpass";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.INPUT);
        runner.setModeData("");
        boolean testOk = false;
        try {
            runner.process();
        } catch (RunnerException e) {
            if ((e.getType().equals(RunnerException.TYPE_TIMEOUT)) && (e.getStage().equals(Runner.STAGE_RUN))) {
                testOk = true;
            }
        }

        assertTrue(testOk);
    }

    public void testBuildAndRunOkWithStdin() {
        DirectoryCleaner.clean();

        String solution = "import fileinput\n" +
                "\n" +
                "# Reads each line from STDIN\n" +
                "for line in fileinput.input():\n" +
                "    print line";

        String stdin = "1\n2\n3";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.INPUT);
        runner.setModeData(stdin);

        boolean testOk = true;
        try {
            runner.process();
        } catch (RunnerException e) {
            testOk = false;
        }

        assertTrue(testOk);
    }


    public void testBuildAndRunOkWithTest() {
        DirectoryCleaner.clean();

        String solution = "def solutionMethod():\n" +
                "\treturn True\n";

        String test = "import unittest\n" +
                "import solution\n" +
                "\n" +
                "class TestMethods(unittest.TestCase):\n" +
                "\n" +
                "    def test_1(self):\n" +
                "        self.assertTrue(solution.solutionMethod())\n" +
                "\n" +
                "    def test_2(self):\n" +
                "        self.assertTrue(solution.solutionMethod2())\n";

        Runner runner = new PythonRunner();
        runner.setSolution(solution);
        runner.setMode(Runner.TestMode.TEST);
        runner.setModeData(test);

        boolean testOk = true;
        try {
            runner.process();
        } catch (RunnerException e) {
            e.printStackTrace();
            testOk = false;
        }

        assertTrue(testOk);
    }
}
