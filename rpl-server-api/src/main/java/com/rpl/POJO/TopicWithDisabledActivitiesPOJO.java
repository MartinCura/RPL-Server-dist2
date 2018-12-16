package com.rpl.POJO;

import java.util.ArrayList;

import com.rpl.model.Activity;
import com.rpl.model.DatabaseState;
import com.rpl.model.Topic;

public class TopicWithDisabledActivitiesPOJO extends TopicPOJO{

    public TopicWithDisabledActivitiesPOJO(Topic topic) {
        super(topic);
        activities = new ArrayList<>();
        for (Activity activity : topic.getActivities()) {
            if (!activity.getState().equals(DatabaseState.DELETED)) {
                activities.add(new ActivityPOJO(activity));
            }
        }
    }

}
