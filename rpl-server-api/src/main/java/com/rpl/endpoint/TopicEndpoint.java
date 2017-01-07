package com.rpl.endpoint;

import com.rpl.POJO.ActivityPOJO;
import com.rpl.POJO.TopicPOJO;
import com.rpl.model.Activity;
import com.rpl.model.Topic;
import com.rpl.service.ActivityService;
import com.rpl.service.TopicService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/topics")
public class TopicEndpoint {
    @Inject
    private TopicService topicService;
    @Inject
    private ActivityService activityService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("id") Long id) {

        Topic topic = topicService.getTopicById(id);
        return Response.status(200).entity(new TopicPOJO(topic)).build();
    }

    @GET
    @Path("/{id}/activities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities(@PathParam("id") Long topicId) {

        List<Activity> activities = activityService.getActivitiesByTopic(topicId);
        List<ActivityPOJO> activityPOJOS = new ArrayList<ActivityPOJO>();
        for (Activity activity : activities) {
            activityPOJOS.add(new ActivityPOJO(activity));
        }
        return Response.status(200).entity(activityPOJOS).build();
    }
}