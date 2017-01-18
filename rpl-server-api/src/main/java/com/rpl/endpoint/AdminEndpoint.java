package com.rpl.endpoint;

import com.rpl.POJO.CourseInputPOJO;
import com.rpl.POJO.CoursePersonInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.RoleCourse;
import com.rpl.service.CourseService;
import com.rpl.service.PersonService;
import com.rpl.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Secured
@Path("/admin")
public class AdminEndpoint {

    @Inject
    private CourseService courseService;
    @Inject
    private PersonService personService;

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
    public Response addProfessor(@PathParam("id") Long id, CoursePersonInputPOJO coursePersonInputPOJO) {
        Course course = courseService.getCourseById(id);
        Person person = personService.getPersonById(coursePersonInputPOJO.getPersonId());

        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setCourse(course);
        coursePerson.setPerson(person);
        coursePerson.setRole(RoleCourse.valueOf(coursePersonInputPOJO.getRole()));
        coursePerson.setAccepted(true);
        personService.addCoursePerson(coursePerson);
        return Response.status(200).build();
    }

}
