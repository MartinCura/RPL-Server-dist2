package com.rpl.runner.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Result {
    private Status status;

    private String stdout;

    @JsonUnwrapped
    private Tests tests;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }
}
