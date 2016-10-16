package com.rpl.runner.exception;

public class BuildException extends RunnerException {

    public BuildException(String messageContent) {
        this.type = "build";
        this.messageContent = messageContent;
    }
}
