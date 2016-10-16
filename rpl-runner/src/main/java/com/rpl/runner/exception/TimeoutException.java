package com.rpl.runner.exception;

public class TimeoutException extends RunnerException {
    public TimeoutException() {
        this.messageContent = "Execution was killed by timeout.";
        this.type = "run";
    }
}
