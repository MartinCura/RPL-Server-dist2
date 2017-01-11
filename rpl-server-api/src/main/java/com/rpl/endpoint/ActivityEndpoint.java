package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivityPOJO;
import com.rpl.POJO.ActivitySubmissionPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplQueueException;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.service.ActivityService;
import com.rpl.service.ActivitySubmissionService;

@Secured
@Path("/activities")
public class ActivityEndpoint {

	@Inject
	private ActivityService activityService;
	@Inject
	private ActivitySubmissionService activitySubmissionService;


	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityById(@PathParam("id") Long id) {

		Activity activity = activityService.getActivityById(id);
		return Response.status(200).entity(new ActivityPOJO(activity)).build();

	}

	@GET
	@Path("/{id}/submissions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionsByActivity(@PathParam("id") Long activityId) {

		List<ActivitySubmission> submissions = activitySubmissionService.getSubmissionsByActivity(Long.valueOf(1), activityId);
		List<ActivitySubmissionPOJO> submissionPOJOS = new ArrayList<ActivitySubmissionPOJO>();
		for (ActivitySubmission submission : submissions) {
			submissionPOJOS.add(new ActivitySubmissionPOJO(submission));
		}
		return Response.status(200).entity(submissionPOJOS).build();

	}
	
	@POST
	@Path("/{id}/submission")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivitySubmission(@PathParam("id") Long activityId, ActivitySubmission submission) {
		
		try {
			activitySubmissionService.submit(activityId, submission);
		} catch (RplQueueException e) {
			return Response.status(505).entity(e).build();
		}

		return Response.status(200).build();

	}
}
