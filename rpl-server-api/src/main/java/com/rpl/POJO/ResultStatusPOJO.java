package com.rpl.POJO;

import com.rpl.model.runner.ResultStatus;

public class ResultStatusPOJO {

    private Long id;
    private String result;
    private String stage;
    private String type;
    private String stderr;

    public ResultStatusPOJO(ResultStatus resultStatus) {
        this.id = resultStatus.getId();
        this.result = resultStatus.getResult();
        this.stage = resultStatus.getStage();
        this.type = resultStatus.getType();
        this.stderr = resultStatus.getStderr();
    }

    public Long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getStage() {
        return stage;
    }

    public String getType() {
        return type;
    }

    public String getStderr() {
        return stderr;
    }

}
