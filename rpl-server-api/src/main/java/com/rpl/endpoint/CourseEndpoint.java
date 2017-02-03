package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.input.ActivityInputPOJO;
import com.rpl.POJO.input.AssignAssistantInputPOJO;
import com.rpl.POJO.input.CourseInputPOJO;
import com.rpl.POJO.CoursePOJO;
import com.rpl.POJO.CourseStudentPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.input.TopicInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.Language;
import com.rpl.model.Person;
import com.rpl.model.TestType;
import com.rpl.model.Topic;
import com.rpl.service.ActivityService;
import com.rpl.service.CourseService;
import com.rpl.service.TopicService;

@Secured
@Path("/courses")
public class CourseEndpoint {
	
	@Inject
	private ActivityService activityService;
	@Inject
	private CourseService courseService;
	@Inject
	private TopicService topicService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourses(@QueryParam("role") String role) {
		List<Course> courses;
		if (role == null) {
			courses = courseService.getCourses();
		} else {
			courses = courseService.getCoursesByRole(role);
		}
		Set<Long> coursesInscripted = courseService.getCoursesInscripted();
		List<CoursePOJO> coursePOJOS = new ArrayList<CoursePOJO>();
		for (Course course : courses) {
			CoursePOJO coursePOJO = new CoursePOJO(course);
			coursePOJO.setInscripted(coursesInscripted.contains(course.getId()));
			coursePOJOS.add(coursePOJO);
		}
		return Response.status(200).entity(coursePOJOS).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") Long id) {

		Course course = courseService.getCourseById(id);
		Set<Long> activitiesSelected = activityService.getActivitiesDoneByCourse(id);
		CoursePOJO coursePOJO = new CoursePOJO(course);
		coursePOJO.markActivitiesAsSelected(activitiesSelected);
		return Response.status(200).entity(coursePOJO).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCourseById(@PathParam("id") Long id) {

		courseService.deleteCourseById(id);
		return Response.ok().build();
	}
	
	
	@PUT
	@Path("/{id}/customization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomization(@PathParam("id") Long id, String customization) {

		courseService.updateCustomization(id, customization);
		return Response.ok().build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCourseById(@PathParam("id") Long id, CourseInputPOJO course) {
		courseService.updateCourseName(id, course.getName());
		return Response.ok().build();
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
	public Response submitActivity(@PathParam("id") Long courseId, ActivityInputPOJO activityInputPOJO) {
		Topic topic = topicService.getTopicById(activityInputPOJO.getTopic());
		Activity activity = new Activity();
		activity.setTopic(topic);
		activity.setName(activityInputPOJO.getName());
		activity.setDescription(activityInputPOJO.getDescription());
		activity.setLanguage(Language.valueOf(activityInputPOJO.getLanguage()));
		activity.setPoints(activityInputPOJO.getPoints());
		activity.setTestType(TestType.valueOf(activityInputPOJO.getTestType()));
		activity.setTemplate(activityInputPOJO.getTemplate());
		activity.setInput(activityInputPOJO.getInput());
		activity.setOutput(activityInputPOJO.getOutput());
		activity.setTests(activityInputPOJO.getTests());
		activityService.submit(courseId, activity);
		return Response.status(200).build();
	}

	@POST
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitTopic(@PathParam("id") Long courseId, TopicInputPOJO topicInputPOJO) {
		Topic topic = new Topic();
		topic.setName(topicInputPOJO.getName());
		topicService.submit(courseId, topic);
		return Response.status(200).build();
	}

	@POST
	@Path("/{id}/join")
	@Produces(MediaType.APPLICATION_JSON)
	public Response joinCourse(@PathParam("id") Long courseId) {
		try {
			courseService.join(courseId);
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of("Already joined")).build();
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("/{id}/leave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leaveCourse(@PathParam("id") Long personId) {
		courseService.leaveCourse(personId);
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

	@POST
	@Path("/{courseId}/assistant")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignAssistant(@PathParam("courseId") Long courseId, AssignAssistantInputPOJO assignAssistantInputPOJO) {
		courseService.assignAssistant(courseId, assignAssistantInputPOJO.getStudent(), assignAssistantInputPOJO.getAssistant());
		return Response.status(200).build();
	}
}
