package com.rpl.runner;

import com.rpl.runner.exception.BuildException;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {

    private static int processCount = 0;
    private boolean enableTimeout;

    private ProcessBuilder pb;
    private File stderrFile;
    private File stdoutFile;

    public ProcessRunner(String[] args, boolean enableTimeout) {
        this.enableTimeout = enableTimeout;
        pb = new ProcessBuilder(args);

        // Path to execute the command
        pb.directory(new File(Settings.EXECUTION_PATH));

        processCount++;
        stderrFile = new File(Settings.ERROR_FILE + processCount);
        stdoutFile = new File(Settings.OUTPUT_FILE + processCount);

        pb.redirectError(ProcessBuilder.Redirect.appendTo(stderrFile));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(stdoutFile));
    }

    // Starts the process and waits until it is finished
    public void start() throws RunnerException {
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (enableTimeout) {
                boolean finishOk = p.waitFor(Settings.EXECUTION_TIMEOUT, TimeUnit.MILLISECONDS);

                if (!finishOk) {
                    throw new TimeoutException();
                }
            } else {
                p.waitFor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!fileIsEmpty(stderrFile)) {
            throw new BuildException(getStderr());
        }

    }

    private boolean fileIsEmpty(File file) {
        return (file.length() == 0);
    }

    public String getStderr() {
        return fileToString(stderrFile);
    }

    public String getStdout() {
        return fileToString(stdoutFile);
    }

    public String fileToString(File file) {
        if (fileIsEmpty(file))
            return "as";

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();

        return text;
    }
}
