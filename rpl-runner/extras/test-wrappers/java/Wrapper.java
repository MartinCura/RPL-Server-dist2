import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import org.junit.Test;

public class Wrapper {

    public static void main( String[] args ) {
        Result result = JUnitCore.runClasses(TestSolution.class);

        String resultStr = "{\"success\": " + result.wasSuccessful() + ", " +
                "\"tests\": [";

        List<String> testsFailed = new ArrayList<>();
        for (Failure failure : result.getFailures()){
            testsFailed.add(failure.getDescription().getMethodName());
            resultStr += "{\"name\": \"" + failure.getDescription().getMethodName() + "\", " +
                "\"success\": false, " +
                "\"description\": \"" + failure.getException().getClass().getName() + "\"}, ";
        }
        for (Method method : TestSolution.class.getMethods()) {
            if(method.isAnnotationPresent(Test.class) && !testsFailed.contains(method.getName())) {
                resultStr += "{\"name\": \"" + method.getName() + "\", " +
                    "\"success\": true, " +
                    "\"description\": \"passed\"},";
            }
        }
        resultStr += "]}";
        System.out.println(resultStr);
    }

}
