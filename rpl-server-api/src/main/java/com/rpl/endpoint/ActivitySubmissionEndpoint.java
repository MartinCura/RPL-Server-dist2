package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivitySubmissionPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplException;
import com.rpl.exception.RplItemDoesNotBelongToPersonException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.MessageCodes;
import com.rpl.model.RoleCourse;
import com.rpl.security.SecurityHelper;
import com.rpl.service.ActivitySubmissionService;
import com.rpl.service.UserService;
import com.rpl.service.util.Utils;

@Secured
@Path("/submissions")
public class ActivitySubmissionEndpoint {

	@Inject
	private ActivitySubmissionService activitySubmissionService;
	@Inject
	private UserService userService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionById(@PathParam("id") Long id) {
		ActivitySubmission submission = activitySubmissionService.getSubmissionById(id);
		if (submission == null) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_INEXISTENT_SUBMISSION, "")).build();
		}
		try {
			if (RoleCourse.STUDENT.equals(SecurityHelper.findRoleOnCourse(
					submission.getActivity().getTopic().getCourse().getId(), userService.getCurrentUser()))) {
				SecurityHelper.checkSubmissionBelongsToPerson(submission, userService.getCurrentUser());
			}
		} catch (RplItemDoesNotBelongToPersonException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		return Response.status(200).entity(new ActivitySubmissionPOJO(submission)).build();
	}
	
	@GET
	@Path("/activity/{activityId}/person/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionById(@PathParam("personId") Long personId, @PathParam("activityId") Long activityId) {
		try {
			SecurityHelper.checkPermissionsByActivityId(activityId, Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		List<ActivitySubmission> submissions = activitySubmissionService.getSubmissionsByActivity(personId, activityId);
		List<ActivitySubmissionPOJO> submissionPOJOS = new ArrayList<>();
		for (ActivitySubmission submission : submissions) {
			submissionPOJOS.add(new ActivitySubmissionPOJO(submission));
		}
		return Response.status(200).entity(submissionPOJOS).build();
	}	

	@POST
	@Path("/{id}/select")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markAsSelected(@PathParam("id") Long submissionId) {
		ActivitySubmission submission = activitySubmissionService.getSubmissionById(submissionId);
		if (submission == null) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_INEXISTENT_SUBMISSION, "")).build();
		}
		try {
			if (RoleCourse.STUDENT.equals(SecurityHelper.findRoleOnCourse(
					submission.getActivity().getTopic().getCourse().getId(), userService.getCurrentUser()))) {
				SecurityHelper.checkSubmissionBelongsToPerson(submission, userService.getCurrentUser());
			}
		} catch (RplItemDoesNotBelongToPersonException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}

		try {
			activitySubmissionService.markAsSelected(submissionId);
		} catch (RplException e) {
			return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
		}
		return Response.status(200).build();
	}

	@POST
	@Path("/{id}/definitive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markAsDefinitive(@PathParam("id") Long submissionId) {
		ActivitySubmission submission = activitySubmissionService.getSubmissionById(submissionId);
		if (submission == null) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_INEXISTENT_SUBMISSION, "")).build();
		}
		try {
			if (RoleCourse.STUDENT.equals(SecurityHelper.findRoleOnCourse(
					submission.getActivity().getTopic().getCourse().getId(), userService.getCurrentUser()))) {
				SecurityHelper.checkSubmissionBelongsToPerson(submission, userService.getCurrentUser());
			}
		} catch (RplItemDoesNotBelongToPersonException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}

		try {
			activitySubmissionService.markAsDefinitive(submissionId);
		} catch (RplException e) {
			return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
		}
		return Response.status(200).build();
	}
}
