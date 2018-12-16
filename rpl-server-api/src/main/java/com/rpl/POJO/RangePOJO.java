package com.rpl.POJO;

import com.rpl.model.Range;

public class RangePOJO {

    private Integer minScore;

    private String rangeName;

    public RangePOJO(Range r) {
        this.setMinScore(r.getMinScore());
        this.setRangeName(r.getRangeName());
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public String getRangeName() {
        return rangeName;
    }

    public void setRangeName(String rangeName) {
        this.rangeName = rangeName;
    }

}
