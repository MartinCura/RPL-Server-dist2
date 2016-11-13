package com.rpl.model.runner;

public class ResultStatus {
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_OK = "ok";

    private String result;
    private String stage;
    private String type;
    private String stderr;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
}
