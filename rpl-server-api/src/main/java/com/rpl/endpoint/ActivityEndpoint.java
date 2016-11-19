package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.exception.RplQueueException;
import com.rpl.model.Activity;
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
	public Response getSActivityById(@PathParam("id") Long id) {

		Activity activity = activityService.getActivityById(id);
		return Response.status(200).entity(activity).build();

	}
	
	@POST
	@Path("/{id}/submission")
	@Produces(MediaType.APPLICATION_JSON)
	public Response submit(@PathParam("id") Long id, String code) {
		
		try {
			activitySubmissionService.submit(id, code);
		} catch (RplQueueException e) {
			return Response.status(505).entity(e).build();
		}

		return Response.status(200).build();

	}
}
