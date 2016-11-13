package com.rpl.model.runner;

import java.util.List;

public class Tests {

    private boolean success;

    private List<TestResult> tests;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TestResult> getTests() {
        return tests;
    }

    public void setTests(List<TestResult> tests) {
        this.tests = tests;
    }
}
