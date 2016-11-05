import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.PythonRunner;
import com.rpl.runner.runner.Runner;
import com.rpl.runner.utils.DirectoryCleaner;
import junit.framework.TestCase;

public class PythonTest extends TestCase {

    public void testPython1() {
        DirectoryCleaner.clean();

        String solution = "print 'Hello World'";

        Runner runner = new PythonRunner();
        boolean ok = true;
        try {
            runner.process(solution);
        } catch (RunnerException e) {
            ok = false;
        }

        assertTrue(ok);
    }
}
