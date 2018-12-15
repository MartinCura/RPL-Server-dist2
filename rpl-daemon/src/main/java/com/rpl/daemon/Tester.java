package com.rpl.daemon;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import com.rpl.model.*;
import com.rpl.model.runner.Result;
import com.rpl.model.runner.ResultStatus;
import com.rpl.service.util.ArrayUtils;
import com.rpl.service.util.FileUtils;
import com.rpl.service.util.JsonUtils;

public class Tester {
	private final String TMP_DIRECTORY = "/tmp/";
	private final String TMP_EXTENSION = ".tmp";
	
	Result runSubmission(ActivitySubmission submission) throws IOException, InterruptedException {
		String[] command = prepareCommand(submission);		
		System.out.println(Arrays.toString(command));
		ProcessBuilder pb = new ProcessBuilder(command);
		File file = new File(TMP_DIRECTORY + submission.getId() + TMP_EXTENSION);

		pb.redirectOutput(file);
		Process process = pb.start();
		process.waitFor();

		String resultString = FileUtils.fileToString(file);
		Result result = JsonUtils.jsonToObject(resultString, Result.class);
		result.setIds(submission.getId());
        if (result.getTests() != null) {
            result.getTests().fixTestsResults();
        }
        return result;
	}
	
	public String[] prepareCommand(ActivitySubmission submission) {
		Activity activity = submission.getActivity();
		String data;
		if (activity.getTestType().equals(TestType.INPUT)) {
			data = activity.getInput();
		} else {
			data = activity.getTests();
		}
		if (data == null)
			data = "";
		
		// Command: docker run --rm rpl -l language -m mode -s solution -d data
		String[] args = {"docker", "run", "--rm", "rpl",
				"-l", activity.getLanguage().toString().toLowerCase(),
				"-m", activity.getTestType().toString().toLowerCase(),
				"-s", submission.getCode(),
				"-d", data
			};

		for (ActivityInputFile file : activity.getFiles()) {
			args = ArrayUtils.addElement(args, "-f", file.getFileName(), new String (Base64.getEncoder().encode(file.getContent())));
		}
		return args;
	}
	
	public void analyzeResult(ActivitySubmission submission, Result result) {
		try {
			if (result.getStatus().getResult().equals(ResultStatus.STATUS_OK)) {
				submission.setStatus( isExpectedOutput(submission, result) ? Status.SUCCESS : Status.FAILURE );
			} else {
				submission.setStatus( result.getStatus().getStage().equals("build") ? Status.BUILDING_ERROR : Status.RUNTIME_ERROR );
			}
			
		} catch (Exception e) {	// ToDo: specify
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
