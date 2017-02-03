package com.rpl.daemon;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpl.model.*;
import com.rpl.model.runner.Result;
import com.rpl.model.runner.ResultStatus;
import com.rpl.service.util.ArrayUtils;
import com.rpl.service.util.FileUtils;

public class Tester {
	private String TMP_DIRECTORY = "/tmp/";
	private String TMP_EXTENSION = ".tmp";
	
	public Result runSubmission(ActivitySubmission submission) throws IOException, InterruptedException {

		String[] command = prepareCommand(submission);		
		System.out.println(Arrays.toString(command));
		ProcessBuilder pb = new ProcessBuilder(command);
		File file = new File(TMP_DIRECTORY + submission.getId() + TMP_EXTENSION);

		pb.redirectOutput(file);
		Process process = pb.start();
		process.waitFor();
		String result = FileUtils.fileToString(file);
		System.out.println(result);
		return new ObjectMapper().readValue(result.getBytes(), Result.class);
		
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
		//for (ActivityFile file : activity.getActivityFiles()) {
		//	args = ArrayUtils.addElement(args, "-f", file.getName(), file.getContent());
		//}
		return args;
	}
	
	public void analyzeResult(ActivitySubmission submission, Result result) {
		
		try {
			
			if (result.getStatus().getResult().equals(ResultStatus.STATUS_OK)) {
				submission.setStatus( isExpectedOutput(submission, result) ? Status.SUCCESS : Status.FAILURE );
			} else {
				submission.setStatus( result.getStatus().getStage().equals("build") ? Status.BUILDING_ERROR : Status.RUNTIME_ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isExpectedOutput(ActivitySubmission submission, Result result) {
		if (submission.getActivity().getTestType().equals(TestType.INPUT)) {
			return result.getStdout().trim().equals(submission.getActivity().getOutput());
		} else {
			return result.getTests().isSuccess();
		}
	}

	
}
