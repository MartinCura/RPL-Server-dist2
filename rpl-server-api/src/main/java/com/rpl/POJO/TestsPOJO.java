package com.rpl.POJO;

import com.rpl.model.runner.TestResult;
import com.rpl.model.runner.Tests;

import java.util.ArrayList;
import java.util.List;


public class TestsPOJO {
    private boolean success;
    private List<TestResultPOJO> tests;

    public TestsPOJO(Tests test) {
        this.success = test.isSuccess();
        this.tests = new ArrayList<TestResultPOJO>();
        for (TestResult testResult : test.getTests()) {
            this.tests.add(new TestResultPOJO(testResult));
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public List<TestResultPOJO> getTests() {
        return tests;
    }
}
