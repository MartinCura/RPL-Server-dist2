package com.rpl.endpoint;

import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.rpl.POJO.CourseCustomizationPOJO;
import com.rpl.POJO.CoursePOJO;
import com.rpl.POJO.CourseStudentPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.input.ActivityInputPOJO;
import com.rpl.POJO.input.AssignAssistantInputPOJO;
import com.rpl.POJO.input.CourseInputPOJO;
import com.rpl.POJO.input.TopicInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.Language;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;
import com.rpl.model.TestType;
import com.rpl.model.Topic;
import com.rpl.security.SecurityHelper;
import com.rpl.service.ActivityService;
import com.rpl.service.CourseService;
import com.rpl.service.TopicService;
import com.rpl.service.UserService;
import com.rpl.service.util.Utils;

@Path("/courses")
public class CourseEndpoint {

	@Inject
	private ActivityService activityService;
	@Inject
	private CourseService courseService;
	@Inject
	private TopicService topicService;
	@Inject
	private UserService userService;

	@Secured
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourses(@QueryParam("role") String role) {
		List<Course> courses;
		if (role == null) {
			courses = courseService.getCourses();
		} else {
			courses = courseService.getCoursesByRole(role);
		}
		Map<Long, Boolean> coursesInscripted = courseService.getCoursesInscripted();
		List<CoursePOJO> coursePOJOS = new ArrayList<CoursePOJO>();
		for (Course course : courses) {
			CoursePOJO coursePOJO = new CoursePOJO(course);
			if (coursesInscripted.containsKey(course.getId())) {
				coursePOJO.setInscripted(coursesInscripted.get(course.getId()));
			}
			coursePOJOS.add(coursePOJO);
		}
		return Response.status(200).entity(coursePOJOS).build();

	}

	@Secured
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") Long id) {
		// TODO student, professor o lo que sea pero del curso este.
		Course course = courseService.getCourseById(id);
		Set<Long> activitiesSelected = activityService.getActivitiesSelectedByCourse(id);
		Set<Long> activitiesDefinitive = activityService.getActivitiesDefinitiveByCourse(id);
		CoursePOJO coursePOJO = new CoursePOJO(course);
		coursePOJO.markActivitiesAsSelected(activitiesSelected);
		coursePOJO.markActivitiesAsDefinitive(activitiesDefinitive);
		return Response.status(200).entity(coursePOJO).build();
	}

	@GET
	@Path("/{id}/customization.css")
	@Produces("text/css")
	public Response getCourseCustomization(@PathParam("id") Long id) {
		Course course = courseService.getCourseById(id);
		return Response.ok(course.getCustomization()).build();
	}

	@Secured(Role.ADMIN)
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCourseById(@PathParam("id") Long id) {
		try {
			SecurityHelper.checkPermissions(id, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.serverError().build();
		}
		courseService.deleteCourseById(id);
		return Response.ok().build();
	}

	@Secured
	@PUT
	@Path("/{id}/customization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomization(@PathParam("id") Long id, CourseCustomizationPOJO pojo) {
		// TODO professor
		courseService.updateDescRulesAndCustomization(id, pojo.getCustomization(), pojo.getDescription(),
				pojo.getRules());
		return Response.ok().build();
	}

	@Secured
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCourseById(@PathParam("id") Long id, CourseInputPOJO course) {
		// TODO professor
		courseService.updateCourseName(id, course.getName());
		return Response.ok().build();
	}

	@Secured
	@GET
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseTopicsById(@PathParam("id") Long id) {
		// TODO professor
		Course course = courseService.getCourseById(id);
		return Response.status(200).entity(new CoursePOJO(course.getTopics())).build();
	}

	@Secured
	@POST
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivity(@PathParam("id") Long courseId, ActivityInputPOJO activityInputPOJO) {
		// TODO PROFESSOR
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

	@Secured
	@POST
	@Path("activity/{id}/file")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@PathParam("id") Long activityId, MultipartFormDataInput input) throws IOException {
		// TODO professor

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {
			String fileName = getFileName(inputPart.getHeaders());
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			activityService.saveFile(activityId, new ActivityInputFile(fileName, bytes));
		}
		return Response.status(200).build();
	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return null;
	}

	@Secured
	@POST
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitTopic(@PathParam("id") Long courseId, TopicInputPOJO topicInputPOJO) {
		// TODO PROFESSOR
		Topic topic = new Topic();
		topic.setName(topicInputPOJO.getName());
		topicService.submit(courseId, topic);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{id}/join")
	@Produces(MediaType.APPLICATION_JSON)
	public Response joinCourse(@PathParam("id") Long courseId) {
		try {
			courseService.join(courseId);
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of("Already joined"))
					.build();
		}
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/person/{personId}/leave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leaveCourse(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		// TODO Professor
		courseService.leaveCourse(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@GET
	@Path("/{id}/students")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO mover a /reports
	public Response getStudentsActivities(@PathParam("id") Long id) {
		Map<Person, Set<ActivitySubmission>> submissionsByPerson = courseService.getSubmissionsByStudent(id);
		return Response.status(200).entity(new CourseStudentPOJO(id, submissionsByPerson)).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/person/{personId}/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptStudent(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		// TODO professor
		courseService.accept(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/person/{personId}/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pendingStudent(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		// TODO professor
		courseService.pending(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/assistant")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignAssistant(@PathParam("courseId") Long courseId,
			AssignAssistantInputPOJO assignAssistantInputPOJO) {
		//TODO professor
		courseService.assignAssistant(courseId, assignAssistantInputPOJO.getStudent(),
				assignAssistantInputPOJO.getAssistant());
		return Response.status(200).build();
	}
}
