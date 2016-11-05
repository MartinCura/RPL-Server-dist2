package com.rpl.runner.tests;

import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.JavaRunner;
import com.rpl.runner.runner.Runner;
import junit.framework.TestCase;

public class JavaTest extends TestCase {

    public void testBuildAndRunOk() {
        DirectoryCleaner.clean();

        String solution = "public class Solution {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World\");\n" +
                "    }\n" +
                "\n" +
                "}\n";

        Runner runner = new JavaRunner();
        runner.setSolution(solution);
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

        String solution = "public class Solution ASDASDASD\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World\");\n" +
                "    }\n" +
                "\n" +
                "}\n";

        Runner runner = new JavaRunner();
        runner.setSolution(solution);
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

        String solution = "public class Solution {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        throw new RuntimeException();\n" +
                "    }\n" +
                "\n" +
                "}\n";

        Runner runner = new JavaRunner();
        runner.setSolution(solution);
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

        String solution = "public class Solution {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World\");\n" +
                "        int a = 0;\n" +
                "        while(true) {\n" +
                "            a++;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n";

        Runner runner = new JavaRunner();
        runner.setSolution(solution);
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
}
