package com.rpl.endpoint;

import com.rpl.POJO.ActivitySubmissionSolutionPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.ReportCoursePOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.*;
import com.rpl.security.SecurityHelper;
import com.rpl.service.*;
import com.rpl.service.util.Utils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	@Inject
	private PersonService personService;

	@GET
	@Path("/course/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsActivities(@PathParam("id") Long courseId) {
		Course course = courseService.getCourseById(courseId);
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), courseId);

		List<CoursePerson> persons;
		Map<Activity, List<ActivitySubmission>> submissionsByActivity;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			persons = courseService.getStudentsByAssistant(courseId, person.getPerson().getId());
			submissionsByActivity = reportService.getReportByCourse(courseId, person.getPerson().getId());
		} else {
			persons = courseService.getStudents(courseId);
			submissionsByActivity = reportService.getReportByCourse(courseId, null);
		}
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
