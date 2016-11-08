import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Wrapper {
	
	public static void main( String[] args ) {
		Result result = JUnitCore.runClasses(TestSolution.class);

		String resultStr = "{\"total\": " + result.getRunCount() + ", " +
				"\"success\": " + (result.getRunCount() - result.getFailureCount()) + ", " +
				"\"failure\": " + result.getFailureCount() + ", " + 
				"\"tests\": [";
		
		for (Failure failure : result.getFailures()){
			resultStr += "\"name\": \"" + failure.getDescription().getMethodName() + "\", " +
				"\"error\": \"" + failure.getException().getClass().getName() + "\", ";
		}
		resultStr += "]}";
		System.out.println(resultStr);
	}

}
