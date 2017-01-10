package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.rpl.POJO.CoursePOJO;
import com.rpl.POJO.CourseStudentPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.Person;
import com.rpl.service.ActivityService;
import com.rpl.service.CourseService;

@Secured
@Path("/courses")
public class CourseEndpoint {
	
	@Inject
	private ActivityService activityService;
	@Inject
	private CourseService courseService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourses() {

		List<Course> courses = courseService.getCourses();
		List<CoursePOJO> coursePOJOS = new ArrayList<CoursePOJO>();
		for (Course course : courses) {
			coursePOJOS.add(new CoursePOJO(course));
		}
		return Response.status(200).entity(coursePOJOS).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") Long id) {

		Course course = courseService.getCourseById(id);
		return Response.status(200).entity(new CoursePOJO(course)).build();
	}

	@GET
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivities(@PathParam("id") Long courseId) {

		List<Activity> activities = activityService.getActivitiesByCourse(courseId);
		List<ActivityPOJO> activityPOJOS = new ArrayList<ActivityPOJO>();
		for (Activity activity : activities) {
			activityPOJOS.add(new ActivityPOJO(activity));
		}
		return Response.status(200).entity(activityPOJOS).build();
	}

	@POST
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivity(@PathParam("id") Long courseId, Activity activity) {
		
		activityService.submit(courseId, activity);
		return Response.status(200).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitCourse(Course course) {

		courseService.submit(course);
		return Response.status(200).build();
	}

	@POST
	@Path("/{id}/join")
	@Produces(MediaType.APPLICATION_JSON)
	public Response joinCourse(@PathParam("id") Long courseId) {
		//FIXME buscar persona por token
		courseService.join(Long.valueOf(1), courseId);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsActivities(@PathParam("id") Long id) {
		Map<Person, Set<ActivitySubmission>> submissionsByPerson = courseService.getSubmissionsByStudent(id);
		return Response.status(200).entity(new CourseStudentPOJO(id, submissionsByPerson)).build();
	}

	@POST
	@Path("/{courseId}/person/{personId}/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptStudent(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		courseService.accept(courseId, personId);
		return Response.status(200).build();
	}
}
