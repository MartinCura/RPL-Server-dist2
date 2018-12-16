package com.rpl.service;

import com.rpl.model.Person;

public interface UserService {
	
	Person getCurrentUser();
	void setCurrentUser(Person p);

}
