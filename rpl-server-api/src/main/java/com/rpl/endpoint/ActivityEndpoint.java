package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.exception.RplQueueException;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.service.ActivityService;
import com.rpl.service.ActivitySubmissionService;

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
		return Response.status(200).entity(activity).build();

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
