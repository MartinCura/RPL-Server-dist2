package com.rpl.service;

import com.rpl.model.Person;

public interface UserService {
	
	public Person getCurrentUser();
	public void setCurrentUser(Person p);

}
