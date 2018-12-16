package com.rpl.runner.exception;

public class StageException extends RunnerException {

    public StageException(String stage, String messageContent) {
        this.type = RunnerException.TYPE_STAGE;
        this.stage = stage;
        this.messageContent = messageContent;
    }
}
