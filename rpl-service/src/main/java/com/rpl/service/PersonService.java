package com.rpl.service;

import java.util.List;

import com.rpl.exception.RplException;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.PersonImage;

public interface PersonService {

    List<Person> getPersons();
	Person getPersonById(Long id);
    Person getPersonByUsername(String username);
	void addCoursePerson(CoursePerson coursePerson) throws RplException;
	void updatePersonInfo(Long id, String name, String mail, Long studentId) throws RplException;
	void updatePersonInfo(Long id, String name, String mail, Long studentId, String role) throws RplException;
	CoursePerson getCoursePersonByIdAndCourse(Long personId, Long courseId);
	void saveImage(Long id, PersonImage courseImage) throws RplException;
	void deletePersonById(Long id);
}
