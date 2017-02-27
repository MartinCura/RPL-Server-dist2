package com.rpl.service;

import com.rpl.exception.RplException;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;

import java.util.List;

public interface PersonService {

    public List<Person> getPersons();
	public Person getPersonById(Long id);
    public Person getPersonByUsername(String username);
	public void addCoursePerson(CoursePerson coursePerson) throws RplException;
	public void updatePersonInfo(Long id, String name, String mail);
}
