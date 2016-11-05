package com.rpl.runner;

import com.rpl.runner.exception.StageException;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.exception.TimeoutException;
import com.rpl.runner.utils.FileUtils;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {

    private static int processCount = 0;
    private boolean enableTimeout;

    private ProcessBuilder pb;
    private File stderrFile;
    private File stdoutFile;

    private String stdin = "";
    private String stage;

    public ProcessRunner(String[] args, boolean enableTimeout, String stage) {
        this.enableTimeout = enableTimeout;
        this.stage = stage;

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

            // Write to STDIN from process
            if (stdin.length() > 0) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
                writer.write(stdin);
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (enableTimeout) {
                boolean finishOk = p.waitFor(Settings.EXECUTION_TIMEOUT, TimeUnit.MILLISECONDS);

                if (!finishOk) {
                    p.destroy();
                    throw new TimeoutException(stage);
                }
            } else {
                p.waitFor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!FileUtils.fileIsEmpty(stderrFile)) {
            throw new StageException(stage, getStderr());
        }

    }

    public String getStderr() {
        return FileUtils.fileToString(stderrFile);
    }

    public String getStdout() {
        return FileUtils.fileToString(stdoutFile);
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }
}
