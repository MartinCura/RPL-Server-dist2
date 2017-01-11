package com.rpl.serviceImpl;

import com.rpl.model.*;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.CourseService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Stateless
public class CourseServiceImpl implements CourseService{

    @Inject
    private CourseDAO courseDAO;
    @Inject
    private CoursePersonDAO coursePersonDAO;
    @Inject
    private PersonDAO personDAO;

    public List<Course> getCourses() {
        return courseDAO.findAll();
    }

    public Course getCourseById(Long id) {
        return courseDAO.find(id);
    }

    public void submit(Course course) {
        courseDAO.save(course);

    }

    public void join(Long personId, Long courseId) {
        Person person = personDAO.find(personId);
        Course course = courseDAO.find(courseId);
        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setPerson(person);
        coursePerson.setCourse(course);
        coursePerson.setRole(RoleCourse.STUDENT);
        coursePersonDAO.save(coursePerson);

    }

    public Map<Person, Set<ActivitySubmission>> getSubmissionsByStudent(Long id) {
        Map<Person, Set<ActivitySubmission>> submissionsByStudent = new HashMap<Person, Set<ActivitySubmission>>();
        List<Person> students = personDAO.findByCourse(id);
        //FIXME devolver solo las submissions de este curso y que fueron SUCCESS
        for (Person person : students) {
            submissionsByStudent.put(person, person.getSubmissions());
        }
        return submissionsByStudent;

    }

    public void accept(Long courseId, Long personId) {
        CoursePerson person = coursePersonDAO.findByCourseAndPerson(courseId, personId);
        person.setAccepted(true);
        coursePersonDAO.save(person);
    }

    public List<CoursePerson> getStudents(Long id) {
        return coursePersonDAO.findByCourseId(id);
    }
}
