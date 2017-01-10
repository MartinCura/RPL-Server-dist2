package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivitySubmissionPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.ActivitySubmission;
import com.rpl.service.ActivitySubmissionService;

@Secured
@Path("/submissions")
public class ActivitySubmissionEndpoint {
	
	@Inject
	private ActivitySubmissionService activitySubmissionService;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionById(@PathParam("id") Long id) {

		ActivitySubmission submission = activitySubmissionService.getSubmissionById(id);
		return Response.status(200).entity(new ActivitySubmissionPOJO(submission)).build();

	}

	@POST
	@Path("/{id}/select")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markAsSelected(@PathParam("id") Long submissionId) {
		activitySubmissionService.markAsSelected(submissionId);
		return Response.status(200).build();
	}
}
