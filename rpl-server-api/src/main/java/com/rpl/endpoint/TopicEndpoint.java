package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivityPOJO;
import com.rpl.model.MessageCodes;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.TopicPOJO;
import com.rpl.POJO.input.TopicInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Activity;
import com.rpl.model.RoleCourse;
import com.rpl.model.Topic;
import com.rpl.security.SecurityHelper;
import com.rpl.service.ActivityService;
import com.rpl.service.TopicService;
import com.rpl.service.UserService;
import com.rpl.service.util.Utils;

@Secured
@Path("/topics")
public class TopicEndpoint {
    @Inject
    private TopicService topicService;
    @Inject
    private ActivityService activityService;
    @Inject
    private UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopicById(@PathParam("id") Long id) {
        try {
            SecurityHelper.checkPermissionsByTopicId(id, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
        } catch (RplRoleException e) {
            return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
        }
        Topic topic = topicService.getTopicById(id);
        return Response.status(200).entity(new TopicPOJO(topic)).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTopicById(@PathParam("id") Long id) {
        try {
            SecurityHelper.checkPermissionsByTopicId(id, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
        } catch (RplRoleException e) {
            return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
        }
        topicService.deleteTopicById(id);
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTopicById(@PathParam("id") Long id, TopicInputPOJO topicPOJO) {
        try {
            SecurityHelper.checkPermissionsByTopicId(id, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
        } catch (RplRoleException e) {
            return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
        }
        Topic updateTopic = new Topic();
        updateTopic.setName(topicPOJO.getName());
        updateTopic.setId(id);
        topicService.update(updateTopic);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/activities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities(@PathParam("id") Long topicId) {
        List<Activity> activities = activityService.getActivitiesByTopic(topicId);
        List<ActivityPOJO> activityPOJOS = new ArrayList<>();
        for (Activity activity : activities) {
            activityPOJOS.add(new ActivityPOJO(activity));
        }
        return Response.status(200).entity(activityPOJOS).build();
    }
    
    @POST
    @Path("{id}/hide")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hideTopic(@PathParam("id") Long topicId) {
        try {
            SecurityHelper.checkPermissionsByActivityId(topicId, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
        } catch (RplRoleException e) {
            return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
        }
        topicService.hide(topicId);
        return Response.status(200).build();
    }

    @POST
    @Path("{id}/unhide")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unhideTopic(@PathParam("id") Long topicId) {
        try {
            SecurityHelper.checkPermissionsByActivityId(topicId, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
        } catch (RplRoleException e) {
            return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
        }
        topicService.unhide(topicId);
        return Response.status(200).build();
    }
}
