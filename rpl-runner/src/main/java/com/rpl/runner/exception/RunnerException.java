package com.rpl.runner.exception;

public class RunnerException extends Exception {

    public static final String TYPE_STAGE = "stage";
    public static final String TYPE_TIMEOUT = "timeout";

    protected String type;
    protected String messageContent;
    protected String stage;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
