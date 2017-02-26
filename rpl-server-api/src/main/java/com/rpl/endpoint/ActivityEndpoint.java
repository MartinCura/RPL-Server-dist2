package com.rpl.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.rpl.POJO.*;
import com.rpl.POJO.input.ActivityInputPOJO;
import com.rpl.POJO.input.ActivitySubmissionInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplQueueException;
import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Language;
import com.rpl.model.TestType;
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
	@Path("/{id}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityCompleteById(@PathParam("id") Long id) {
		// TODO professor
		Activity activity = activityService.getActivityById(id);
		return Response.status(200).entity(new ActivityCompletePOJO(activity)).build();

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteActivityById(@PathParam("id") Long id) {
		// TODO professor
		activityService.delete(id);
		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateActivityById(@PathParam("id") Long id, ActivityInputPOJO activity) {
		// TODO professor
		activityService.update(id, activity.getName(), activity.getDescription(),
				Language.valueOf(activity.getLanguage()), activity.getPoints(), activity.getTopic(),
				TestType.valueOf(activity.getTestType()), activity.getTemplate(), activity.getInput(),
				activity.getOutput(), activity.getTests());
		return Response.ok().build();
	}

	@GET
	@Path("/{id}/submissions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionsByActivity(@PathParam("id") Long activityId) {
		List<ActivitySubmission> submissions = activitySubmissionService.getSubmissionsByActivity(activityId);
		List<ActivitySubmissionSimplePOJO> submissionPOJOS = new ArrayList<ActivitySubmissionSimplePOJO>();
		for (ActivitySubmission submission : submissions) {
			submissionPOJOS.add(new ActivitySubmissionSimplePOJO(submission));
		}
		return Response.status(200).entity(submissionPOJOS).build();

	}

	@POST
	@Path("/{id}/submission")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivitySubmission(@PathParam("id") Long activityId,
			ActivitySubmissionInputPOJO submissionPOJO) {
		// TODO student
		try {
			ActivitySubmission submission = new ActivitySubmission();
			submission.setCode(submissionPOJO.getCode());
			submission = activitySubmissionService.submit(activityId, submission);
			activitySubmissionService.queueSubmission(submission.getId());
			return Response.status(200).entity(new ActivitySubmissionPOJO(submission)).build();
		} catch (RplQueueException e) {
			return Response.status(505).entity(e).build();
		}
	}

	@DELETE
	@Path("{id}/file")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteFile(@PathParam("id") Long fileId) throws IOException {
		// TODO professor
		activityService.deleteFile(fileId);
		return Response.status(200).build();
	}

	@GET
	@Path("{id}/files")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFiles(@PathParam("id") Long activityId) throws IOException {
		// TODO professor
		List<ActivityInputFile> list = activityService.findAllFiles(activityId);
		return Response.status(200)
				.entity(list.stream().map(f -> new ActivityInputFilePOJO(f)).collect(Collectors.toList())).build();
	}

	@GET
	@Path("/{id}/solutions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDefinitiveSubmissionsByActivity(@PathParam("id") Long activityId) {
		// TODO kevin: check the student has already sent a definitive solution
		List<ActivitySubmission> submissions = activitySubmissionService.getDefinitiveSubmissionsByActivity(activityId);
		List<ActivitySubmissionSolutionPOJO> submissionPOJOS = new ArrayList<ActivitySubmissionSolutionPOJO>();
		for (ActivitySubmission submission : submissions) {
			submissionPOJOS.add(new ActivitySubmissionSolutionPOJO(submission));
		}
		return Response.status(200).entity(submissionPOJOS).build();

	}
}
