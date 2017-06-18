package com.rpl.service;

import java.util.List;

import com.rpl.exception.RplException;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.PersonImage;

public interface PersonService {

    public List<Person> getPersons();
	public Person getPersonById(Long id);
    public Person getPersonByUsername(String username);
	public void addCoursePerson(CoursePerson coursePerson) throws RplException;
	public void updatePersonInfo(Long id, String name, String mail, Long studentId) throws RplException;
	public void updatePersonInfo(Long id, String name, String mail, Long studentId, String role) throws RplException;
	public CoursePerson getCoursePersonByIdAndCourse(Long personId, Long courseId);
	public void saveImage(Long id, PersonImage courseImage) throws RplException;
	public void deletePersonById(Long id);
}
