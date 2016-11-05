import com.rpl.runner.Settings;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.PythonRunner;
import com.rpl.runner.runner.Runner;
import com.rpl.runner.utils.DirectoryCleaner;
import junit.framework.TestCase;

public class PythonTest extends TestCase {

    public void testPythonBuildAndRunOk() {
        DirectoryCleaner.clean();

        String solution = "print 'Hello World'";

        Runner runner = new PythonRunner();
        boolean testOk = true;
        try {
            runner.process(solution);
        } catch (RunnerException e) {
            testOk = false;
        }

        assertTrue(testOk);
    }

    public void testPythonBuildNotOk() {
        DirectoryCleaner.clean();

        String solution = "print Hello World'";

        Runner runner = new PythonRunner();
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

    public void testPythonRunNotOk() {
        DirectoryCleaner.clean();

        String solution = "raise Exception('error on runtime!')";

        Runner runner = new PythonRunner();
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

    public void testPythonRunWithTimeout() {
        DirectoryCleaner.clean();
        // Avoid waiting 10s
        Settings.EXECUTION_TIMEOUT = 1000;

        String solution = "while(True):\n" +
                          "\tpass";

        Runner runner = new PythonRunner();
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
