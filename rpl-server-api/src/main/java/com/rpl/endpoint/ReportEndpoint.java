package com.rpl.endpoint;

import com.rpl.POJO.ReportCoursePOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.service.CourseService;
import com.rpl.service.ReportService;

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

	@GET
	@Path("/course/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsActivities(@PathParam("id") Long courseId) {
		Course course = courseService.getCourseById(courseId);
		List<CoursePerson> persons = courseService.getStudents(courseId);
		Map<Activity, List<ActivitySubmission>> submissionsByActivity = reportService.getReportByCourse(courseId);
		return Response.status(200).entity(new ReportCoursePOJO(course, persons, submissionsByActivity)).build();
	}
}
