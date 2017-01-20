package com.rpl.service;

import com.rpl.model.CoursePerson;
import com.rpl.model.Person;

public interface PersonService {

	public Person getPersonById(Long id);
	public void addCoursePerson(CoursePerson coursePerson);
	public void updatePersonInfo(Long id, String name, String mail);
}
