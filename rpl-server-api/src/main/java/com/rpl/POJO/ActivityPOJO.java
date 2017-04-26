package com.rpl.POJO;

import com.rpl.model.Activity;
import com.rpl.model.DatabaseState;

public class ActivityPOJO {
    private Long id;
    private String name;
    private String language;
    private String description;
    private String template;
    private int points;
    private boolean success;
    private boolean definitive;
    private String topicName;
    private boolean enabled;

    public ActivityPOJO(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.language = activity.getLanguage().toString();
        this.description = activity.getDescription();
        this.template = activity.getTemplate();
        this.points = activity.getPoints();
        this.success = false;
        this.setEnabled(DatabaseState.ENABLED.equals(activity.getState()));
        if (activity.getTopic() != null) this.topicName = activity.getTopic().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getPoints() {
        return points;
    }


    public String getDescription() {
        return description;
    }

    public String getTemplate() {
        return template;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isDefinitive() {
        return definitive;
    }

    public void setDefinitive(boolean definitive) {
        this.definitive = definitive;
    }

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
