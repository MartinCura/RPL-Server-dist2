package com.rpl.daemon.tests;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpl.daemon.Tester;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Language;
import com.rpl.model.Status;
import com.rpl.model.TestType;
import com.rpl.model.runner.Result;

public class DaemonTest {
	
	public ActivitySubmission createSubmission() {
		Activity activity = new Activity();
		activity.setId((long) 1);
		activity.setLanguage(Language.PYTHON);
		activity.setName("Test activity");
		activity.setTestType(TestType.INPUT);
		activity.setInput("input");
		activity.setOutput("hello");
		
		ActivitySubmission submission = new ActivitySubmission();
		submission.setId((long) 1);
		submission.setCode("print 'hello'");
		submission.setActivity(activity);
		return submission;
	}
	
	@Test
	public void testPrepareCommand() {
		ActivitySubmission submission = createSubmission();
		Tester tester = new Tester();
		String[] command = tester.prepareCommand(submission);
		String[] expectedCommand = {"docker", "run", "--rm", "rpl", "-l", "python", "-m", "input", "-s", "print 'hello'", "-d", "input"};
		Assert.assertArrayEquals(command, expectedCommand);
	}
	
	@Test
	public void testAnalyzeOutputSuccess() {
		ActivitySubmission submission = createSubmission();
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"ok\",\"stage\":null,\"type\":null,\"stderr\":null},\"stdout\":\"hello\\n\"}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.SUCCESS, submission.getStatus());
	}

	@Test
	public void testAnalyzeOutputFailure() {
		ActivitySubmission submission = createSubmission();
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"ok\",\"stage\":null,\"type\":null,\"stderr\":null},\"stdout\":\"hello2\\n\"}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.FAILURE, submission.getStatus());
		
	}
	
	@Test
	public void testAnalyzeOutputRuntimeError() {
		ActivitySubmission submission = createSubmission();
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"error\",\"stage\":\"run\",\"type\":\"stage\",\"stderr\":\"Traceback (most recent call last):\\n  File \\\"solution.py\\\", line 1, in <module>\\n    a = b+1\\nNameError: name 'b' is not defined\\n\"},\"stdout\":null}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.RUNTIME_ERROR, submission.getStatus());
		
	}
	
	@Test
	public void testAnalyzeOutputBuildingError() {
		ActivitySubmission submission = createSubmission();
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"error\",\"stage\":\"build\",\"type\":\"stage\",\"stderr\":\"  File \\\"solution.py\\\", line 1\\n    prin 'hello'\\n \\nSyntaxError: invalid syntax\\n\\n\"},\"stdout\":null}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.BUILDING_ERROR, submission.getStatus());
	}
	
	@Test
	public void testAnalyzeTestSuccess() {
		ActivitySubmission submission = createSubmission();
		submission.getActivity().setTestType(TestType.TEST);
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"ok\",\"stage\":null,\"type\":null,\"stderr\":null},\"stdout\":null,\"tests\":{\"success\":true,\"tests\":[{\"name\":\"test_1\",\"success\":true,\"description\":\"passed\"},{\"name\":\"test_2\",\"success\":true,\"description\":\"passed\"}]}}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.SUCCESS, submission.getStatus());
	}
	
	@Test
	public void testAnalyzeTestFailure() {
		ActivitySubmission submission = createSubmission();
		submission.getActivity().setTestType(TestType.TEST);
		Tester tester = new Tester();
		String output = "{\"status\":{\"result\":\"ok\",\"stage\":null,\"type\":null,\"stderr\":null},\"stdout\":null,\"tests\":{\"success\":false,\"tests\":[{\"name\":\"test_1\",\"success\":false,\"description\":\"failure\"},{\"name\":\"test_2\",\"success\":true,\"description\":\"passed\"}]}}";
		Result result;
		try {
			result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			tester.analyzeResult(submission, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(Status.FAILURE, submission.getStatus());
	}
	
}
