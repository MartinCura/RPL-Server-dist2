package com.rpl.POJO;

import com.rpl.model.Course;

public class CourseCustomizationPOJO {

	private String description;
	private String customization;
	private String rules;

	public CourseCustomizationPOJO(Course c) {
		rules = c.getRules();
		description = c.getDescription();
		customization = c.getCustomization();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomization() {
		return customization;
	}

	public void setCustomization(String customization) {
		this.customization = customization;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

}
