package com.rpl.runner.exception;

public class TimeoutException extends RunnerException {
    public TimeoutException(String stage) {
        this.type = RunnerException.TYPE_TIMEOUT;
        this.stage = stage;
        this.messageContent = "Execution was killed by timeout.";
    }
}
