package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response getCourses(@QueryParam("role") String role) {
		List<Course> courses;
		if (role == null) {
			courses = courseService.getCourses();
		} else {
			courses = courseService.getCoursesByRole(role);
		}
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
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCourseById(@PathParam("id") Long id) {

		courseService.deleteCourseById(id);
		return Response.ok().build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCourseById(Course course) {

		Course updatedCourse = courseService.submit(course);
		return Response.status(200).entity(new CoursePOJO(updatedCourse)).build();
	}
	
	@GET
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseTopicsById(@PathParam("id") Long id) {

		Course course = courseService.getCourseById(id);
		return Response.status(200).entity(new CoursePOJO(course.getTopics())).build();
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
		courseService.join(courseId);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}/students")
	@Produces(MediaType.APPLICATION_JSON)
	//TODO mover a /reports
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
