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
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.rpl.POJO.CourseCustomizationPOJO;
import com.rpl.POJO.CoursePOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.RankingPOJO;
import com.rpl.POJO.input.ActivityInputPOJO;
import com.rpl.POJO.input.AssignAssistantInputPOJO;
import com.rpl.POJO.input.CourseInputPOJO;
import com.rpl.POJO.input.TopicInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.Course;
import com.rpl.model.CourseImage;
import com.rpl.model.CoursePerson;
import com.rpl.model.Language;
import com.rpl.model.MessageCodes;
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
import com.rpl.service.util.FileUtils;
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
		Map<Long, CoursePerson> coursesInscripted = courseService.getCoursesInscripted();
		List<CoursePOJO> coursePOJOS = new ArrayList<CoursePOJO>();
		for (Course course : courses) {
			CoursePOJO coursePOJO = new CoursePOJO(course);
			coursePOJO.setInscripted(coursesInscripted.get(course.getId()));
			coursePOJOS.add(coursePOJO);
		}
		return Response.status(200).entity(coursePOJOS).build();

	}

	@Secured
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") Long id) {
		try {
			SecurityHelper.checkPermissions(id,
					Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.STUDENT, RoleCourse.ASSISTANT_PROFESSOR),
					userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
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
		courseService.deleteCourseById(id);
		return Response.ok().build();
	}

	@Secured
	@PUT
	@Path("/{id}/customization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomization(@PathParam("id") Long id, CourseCustomizationPOJO pojo) {
		try {
			SecurityHelper.checkPermissions(id, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		courseService.updateDescRulesAndCustomization(id, pojo.getCustomization(), pojo.getDescription(),
				pojo.getRules());
		return Response.ok().build();
	}

	@Secured
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCourseById(@PathParam("id") Long id, CourseInputPOJO course) {
		courseService.updateCourseName(id, course.getName());
		return Response.ok().build();
	}

	@Secured
	@GET
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseTopicsById(@PathParam("id") Long id) {
		try {
			SecurityHelper.checkPermissions(id, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		Course course = courseService.getCourseById(id);
		return Response.status(200).entity(new CoursePOJO(course.getTopics())).build();
	}

	@Secured
	@POST
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitActivity(@PathParam("id") Long courseId, ActivityInputPOJO activityInputPOJO) {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
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
		try {
			SecurityHelper.checkPermissionsByActivityId(activityId, Utils.listOf(RoleCourse.PROFESSOR),
					userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {

			String[] contentDisposition = inputPart.getHeaders().getFirst("Content-Disposition").split(";");

			String fileName = FileUtils.getFileName(contentDisposition);
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			try {
				activityService.saveFile(activityId, new ActivityInputFile(fileName, bytes));
			} catch (RplException e) {
				return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
			}
		}
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}/image")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("image/*")
	public Response getPersonImage(@PathParam("id")Long courseId) {
		Course c = courseService.getCourseById(courseId);
		return Response.status(200).entity((c.getCourseImage() != null)? c.getCourseImage().getContent() : null).build();

	}
	
	@Secured
	@POST
	@Path("{id}/image")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(@PathParam("id") Long courseId, MultipartFormDataInput input) throws IOException {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {

			String[] contentDisposition = inputPart.getHeaders().getFirst("Content-Disposition").split(";");

			String fileName = FileUtils.getFileName(contentDisposition);
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			try {
				courseService.saveImage(courseId, new CourseImage(fileName, bytes));
			} catch (RplException e) {
				return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
			}
		}
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response submitTopic(@PathParam("id") Long courseId, TopicInputPOJO topicInputPOJO) {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
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

	@Secured(Role.ADMIN)
	@POST
	@Path("/{courseId}/person/{personId}/leave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leaveCourse(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		courseService.leaveCourse(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/person/{personId}/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptStudent(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		courseService.accept(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/person/{personId}/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pendingStudent(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		courseService.pending(courseId, personId);
		return Response.status(200).build();
	}

	@Secured
	@POST
	@Path("/{courseId}/assistant")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignAssistant(@PathParam("courseId") Long courseId,
			AssignAssistantInputPOJO assignAssistantInputPOJO) {
		try {
			SecurityHelper.checkPermissions(courseId, Utils.listOf(RoleCourse.PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		courseService.assignAssistant(courseId, assignAssistantInputPOJO.getStudent(),
				assignAssistantInputPOJO.getAssistant());
		return Response.status(200).build();
	}

	@Secured
	@GET
	@Path("/{id}/ranking")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRanking(@PathParam("id") Long id) {
		try {
			SecurityHelper.checkPermissions(id, Utils.listOf(RoleCourse.STUDENT), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		List<RankingPOJO> ranking = new ArrayList<RankingPOJO>();
		Map<Person, Integer> pointsByPerson = courseService.getPointsByPerson(id);

		pointsByPerson.entrySet().stream().sorted(Map.Entry.<Person, Integer>comparingByValue().reversed())
				.forEach((entry) -> ranking.add(new RankingPOJO(entry.getKey(), entry.getValue())));

		int pos = 1;
		for (RankingPOJO r : ranking) {
			r.setPos(pos);
			pos++;
		}
		return Response.status(200).entity(ranking).build();
	}
}
