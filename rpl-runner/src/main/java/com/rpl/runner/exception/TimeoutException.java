package com.rpl.runner.exception;

public class TimeoutException extends RunnerException {
    public TimeoutException(String stage) {
        this.type = "timeout";
        this.stage = stage;
        this.messageContent = "Execution was killed by timeout.";
    }
}
