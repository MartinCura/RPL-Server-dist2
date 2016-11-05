package com.rpl.daemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.TestType;

public class Tester {
	private String TMP_DIRECTORY = "/tmp/";
	private String TMP_EXTENSION = ".tmp";
	
	public String runSubmission(ActivitySubmission submission) throws IOException {

		String[] command = prepareCommand(submission);		
		ProcessBuilder pb = new ProcessBuilder(command);
		File file = new File(TMP_DIRECTORY + submission.getId() + TMP_EXTENSION);
		
		pb.redirectOutput(file);
		pb.start();
		return getOutputFromFile(file);
	}
	
	public String[] prepareCommand(ActivitySubmission submission) {
		Activity activity = submission.getActivity();
		String data;
		if (activity.getType().equals(TestType.INPUT_OUTPUT)) {
			data = activity.getInput();
		} else {
			data = activity.getTests();
		}
		
		// Command: docker run --rm rpl -l language -m mode -s solution -d data
		String[] args = {"docker", "run", "--rm", "rpl",
				"-l", activity.getLanguage().toString().toLowerCase(),
				"-m", activity.getType().toString(),
				"-s", submission.getCode(),
				"-d", data
			};
		System.out.println(Arrays.toString(args));
		return args;
	}
	
	public String getOutputFromFile(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            String result = scanner.useDelimiter("\\A").next();
            scanner.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
	}

	public String analyzeOutput(ActivitySubmission submission, String output) {
		//TODO
		return null;
	}
	
	
}
