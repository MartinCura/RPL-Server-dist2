package com.rpl.model.runner;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tests")
public class Tests {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private boolean success;

    @OneToMany
    @JoinColumn(name="tests_id")
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
