package com.rpl.POJO;

import java.util.List;

import com.rpl.model.Range;

public class RangesPOJO {
	
	private List<Range> ranges;

	public RangesPOJO(List<Range> ranges) {
		this.ranges = ranges;
	}
	
	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}

}
