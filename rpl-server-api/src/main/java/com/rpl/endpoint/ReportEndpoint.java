package com.rpl.endpoint;

import com.rpl.POJO.ActivitySubmissionSolutionPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.ReportCoursePOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.*;
import com.rpl.security.SecurityHelper;
import com.rpl.service.ActivitySubmissionService;
import com.rpl.service.CourseService;
import com.rpl.service.ReportService;
import com.rpl.service.UserService;
import com.rpl.service.util.Utils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Secured
@Path("/reports")
public class ReportEndpoint {
	@Inject
	private CourseService courseService;
	@Inject
	private ReportService reportService;
	@Inject
	private ActivitySubmissionService activitySubmissionService;
	@Inject
	private UserService userService;

	@GET
	@Path("/course/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsActivities(@PathParam("id") Long courseId, @QueryParam("assistant") Long assistantId) {
		Course course = courseService.getCourseById(courseId);
		List<CoursePerson> persons;
		if (assistantId != null) {
			persons = courseService.getStudentsByAssistant(courseId, assistantId);
		} else {
			persons = courseService.getStudents(courseId);
		}
		Map<Activity, List<ActivitySubmission>> submissionsByActivity = reportService.getReportByCourse(courseId, assistantId);
		return Response.status(200).entity(new ReportCoursePOJO(course, persons, submissionsByActivity)).build();
	}

	@GET
	@Path("/submission/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionCode(@PathParam("id") Long submissionId) {
		ActivitySubmission submission = activitySubmissionService.getSubmissionById(submissionId);
		try {
			SecurityHelper.checkPermissions(submission.getActivity().getTopic().getCourse().getId(),
					Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		return Response.status(200).entity(new ActivitySubmissionSolutionPOJO(submission)).build();
	}
}
