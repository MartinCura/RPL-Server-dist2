package com.rpl.daemon;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Status;
import com.rpl.model.TestType;
import com.rpl.daemon.result.Result;
import com.rpl.service.util.FileUtils;

public class Tester {
	private String TMP_DIRECTORY = "/tmp/";
	private String TMP_EXTENSION = ".tmp";
	
	public String runSubmission(ActivitySubmission submission) throws IOException, InterruptedException {

		String[] command = prepareCommand(submission);		
		ProcessBuilder pb = new ProcessBuilder(command);
		File file = new File(TMP_DIRECTORY + submission.getId() + TMP_EXTENSION);
		
		pb.redirectOutput(file);
		Process process = pb.start();
		process.waitFor();
		return FileUtils.fileToString(file);
	}
	
	public String[] prepareCommand(ActivitySubmission submission) {
		Activity activity = submission.getActivity();
		String data;
		if (activity.getTestType().equals(TestType.INPUT)) {
			data = activity.getInput();
		} else {
			data = activity.getTests();
		}
		
		// Command: docker run --rm rpl -l language -m mode -s solution -d data
		String[] args = {"docker", "run", "--rm", "rpl",
				"-l", activity.getLanguage().toString().toLowerCase(),
				"-m", activity.getTestType().toString().toLowerCase(),
				"-s", submission.getCode(),
				"-d", data
			};
		return args;
	}
	
	public void analyzeResult(ActivitySubmission submission, String output) {
		
		try {
			Result result = new ObjectMapper().readValue(output.getBytes(), Result.class);
			
			if (result.getStatus().getResult().equals(com.rpl.daemon.result.Status.STATUS_OK)) {
				submission.setExecutionOutput(result.getStdout().trim());
				submission.setStatus( isExpectedOutput(submission) ? Status.SUCCESS : Status.TEST_FAILURE );
			} else {
				submission.setExecutionOutput(result.getStatus().getStderr().trim());
				submission.setStatus( result.getStatus().getStage().equals("build") ? Status.BUILDING_ERROR : Status.RUNTIME_ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isExpectedOutput(ActivitySubmission submission) {
		if (submission.getActivity().getTestType().equals(TestType.INPUT)) {
			return submission.getExecutionOutput().equals(submission.getActivity().getOutput());
		} else {
			//TODO analizar output con tests
			return true;
		}
	}

	
}
