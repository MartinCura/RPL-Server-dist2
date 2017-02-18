package com.rpl.model.runner;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "tests")
public class Tests {
	
	@Id
	private Long id;

    private boolean success;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestResult> tests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void fixTestsResults() {
        for ( TestResult tr : this.tests) {
            tr.setTest(this);
        }
    }
}
