package com.rpl.runner.tests;

import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.CRunner;
import com.rpl.runner.runner.Runner;
import junit.framework.TestCase;

public class CTest extends TestCase {

    public void testBuildAndRunOk() {
        DirectoryCleaner.clean();

        String solution = "#include <stdio.h>\n" +
                "int main(void){\n" +
                "\tint a;\n" +
                "    printf(\"My first C program\\n\");\n" +
                "    return 0;\n" +
                "}";

        Runner runner = new CRunner();
        boolean testOk = true;
        try {
            runner.process(solution);
        } catch (RunnerException e) {
            testOk = false;
        }

        assertTrue(testOk);
    }

    public void testBuildNotOk() {
        DirectoryCleaner.clean();

        String solution = "#include <stdio.h>\n" +
                "{{{{{{{{{int main(void){\n" +
                "\tint a;\n" +
                "    printf(\"My first C program\\n\");\n" +
                "    return 0;\n" +
                "}";

        Runner runner = new CRunner();
        boolean testOk = false;
        try {
            runner.process(solution);
        } catch (RunnerException e) {
            if ((e.getType().equals(RunnerException.TYPE_STAGE)) && (e.getStage().equals(Runner.STAGE_BUILD))) {
                testOk = true;
            }
        }

        assertTrue(testOk);
    }

    public void testRunNotOk() {
        DirectoryCleaner.clean();

        String solution = "#include <stdio.h>\n" +
                "int main(void){\n" +
                "\tint a;\n" +
                "    fprintf(stderr, \"My first C program\\n\");\n" +
                "    return 0;\n" +
                "}";

        Runner runner = new CRunner();
        boolean testOk = false;
        try {
            runner.process(solution);
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

        String solution = "#include <stdio.h>\n" +
                "int main(void){\n" +
                "\tint a;\n" +
                "\twhile(1) { a++;}\n" +
                "    return 0;\n" +
                "}";

        Runner runner = new CRunner();
        boolean testOk = false;
        try {
            runner.process(solution);
        } catch (RunnerException e) {
            if ((e.getType().equals(RunnerException.TYPE_TIMEOUT)) && (e.getStage().equals(Runner.STAGE_RUN))) {
                testOk = true;
            }
        }

        assertTrue(testOk);
    }
}
