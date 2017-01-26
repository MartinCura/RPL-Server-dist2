package com.rpl.POJO;

import com.rpl.model.runner.Result;

public class ResultPOJO {
    private Long id;
    private String stdout;
    private ResultStatusPOJO status;
    private TestsPOJO tests;

    public ResultPOJO(Result result) {
        this.id = result.getId();
        this.stdout = result.getStdout();
        if (result.getStatus() != null)
            this.status = new ResultStatusPOJO(result.getStatus());
        if (result.getTests() != null)
            this.tests = new TestsPOJO(result.getTests());
    }

    public Long getId() {
        return id;
    }

    public String getStdout() {
        return stdout;
    }

    public ResultStatusPOJO getStatus() {
        return status;
    }

    public TestsPOJO getTests() {
        return tests;
    }
}
