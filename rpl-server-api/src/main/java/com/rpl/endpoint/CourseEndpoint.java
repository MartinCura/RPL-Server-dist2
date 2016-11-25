package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.model.Activity;
import com.rpl.service.ActivityService;

@Path("/courses")
public class CourseEndpoint {
	
	@Inject
	private ActivityService activityService;

	
	@POST
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivity(@PathParam("id") Long courseId, Activity activity) {
		
		activityService.submit(courseId, activity);
		return Response.status(200).build();

	}

}
