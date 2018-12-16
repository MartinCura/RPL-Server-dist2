package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.*;
import com.rpl.POJO.input.CourseInputPOJO;
import com.rpl.POJO.input.CoursePersonInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplException;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;
import com.rpl.service.CourseService;
import com.rpl.service.PersonService;
import com.rpl.service.SecurityService;

@Secured(Role.ADMIN)
@Path("/admin")
public class AdminEndpoint {

    @Inject
    private CourseService courseService;
    @Inject
    private PersonService personService;
    @Inject
    private SecurityService securityService;

    @GET
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        List<Course> courses;
        courses = courseService.getCoursesEnabledAndDisabled();
        Map<Long, CoursePerson> coursesInscripted = courseService.getCoursesInscripted();
        List<CoursePOJO> coursePOJOS = new ArrayList<>();
        for (Course course : courses) {
            CoursePOJO coursePOJO = CoursePOJO.mapWithoutTopics(course);
            coursePOJO.setInscripted(coursesInscripted.get(course.getId()));
            coursePOJOS.add(coursePOJO);
        }
        return Response.status(200).entity(coursePOJOS).build();

    }
    
    @POST
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourse(CourseInputPOJO courseInputPOJO) {
        Course course = new Course();
        course.setName(courseInputPOJO.getName());
        courseService.submit(course);
        return Response.status(200).build();
    }

    @POST
    @Path("/courses/{courseId}/person")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPersonToCourse(@PathParam("courseId") Long id, CoursePersonInputPOJO coursePersonInputPOJO) {
        Course course = courseService.getCourseById(id);
        Person person = personService.getPersonByUsername(coursePersonInputPOJO.getUsername());
        if (person == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of(MessageCodes.ERROR_INEXISTENT_USER, "")).build();
        }
        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setCourse(course);
        coursePerson.setPerson(person);
        coursePerson.setRole(RoleCourse.valueOf(coursePersonInputPOJO.getRole()));
        coursePerson.setAccepted(true);
        try {
            personService.addCoursePerson(coursePerson);
        } catch (RplException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of(e.getCode(), e.getMsg())).build();
        }
        return Response.status(200).build();
    }
    
    
    @GET
    @Path("/{id}/assistants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssistantsAdmin(@PathParam("id") Long id) {
        List<CoursePerson> assistants = courseService.getAssistants(id);
        List<AssistantPOJO> assistantPOJOS = new ArrayList<>();
        for (CoursePerson assistant : assistants) {
            assistantPOJOS.add(new AssistantPOJO(assistant));
        }
        return Response.status(200).entity(assistantPOJOS).build();
    }

    @GET
    @Path("/{id}/professors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfessors(@PathParam("id") Long id) {
        List<CoursePerson> professors = courseService.getProfessors(id);

        List<ProfessorPOJO> professorPOJOS = professors.stream().map(ProfessorPOJO::new)
                .collect(Collectors.toList());

        return Response.status(200).entity(professorPOJOS).build();
    }

    @POST
    @Path("/{courseId}/person/{personId}/leave")
    @Produces(MediaType.APPLICATION_JSON)
    public Response leaveCourse(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId) {
        courseService.leaveCourse(courseId, personId);
        return Response.status(200).build();
    }

    @GET
    @Path("/persons/{personId}/information")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonInfo(@PathParam("personId") Long personId) {

        Person p = personService.getPersonById(personId);
        return Response.status(200).entity(new PersonInfoPOJO(p)).build();

    }

    @PUT
    @Path("/persons/{personId}/information")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePersonInfoById(@PathParam("personId") Long personId, PersonInfoPOJO pojo) {
        try {
            personService.updatePersonInfo(personId, pojo.getName(), pojo.getMail(), pojo.getStudentId(), pojo.getRole());
        } catch (RplException e) {
            return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/persons/{personId}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(@PathParam("personId") Long personId, PersonPasswordPOJO pojo) {

        securityService.updatePassword(personId, pojo.getPassword());

        return Response.ok().build();

    }

    @DELETE
    @Path("/persons/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersonById(@PathParam("personId") Long personId) {
        personService.deletePersonById(personId);
        return Response.ok().build();
    }

    @POST
    @Path("/courses/copy/source/{sourceCourseId}/dest/{destCourseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response copyActivities(@PathParam("sourceCourseId") Long sourceCourseId, @PathParam("destCourseId") Long destCourseId) {
        courseService.copyTopics(sourceCourseId, destCourseId);
        courseService.copyActivities(sourceCourseId, destCourseId);
        return Response.status(200).build();
    }
}
