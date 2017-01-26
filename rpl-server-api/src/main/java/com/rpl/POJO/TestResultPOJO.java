package com.rpl.POJO;

import com.rpl.model.runner.TestResult;

public class TestResultPOJO {
    private Long id;
    private String name;
    private String description;
    private boolean success;

    public TestResultPOJO(TestResult testResult) {
        this.id = testResult.getId();
        this.name = testResult.getName();
        this.description = testResult.getDescription();
        this.success = testResult.isSuccess();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSuccess() {
        return success;
    }
}
