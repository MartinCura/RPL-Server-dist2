package com.rpl.service.util;

import java.util.ArrayList;
import java.util.List;

import com.rpl.model.CoursePerson;
import com.rpl.model.Person;

public class Utils {

    public static <T> List<T> listOf(T... elements){
    	ArrayList<T> result = new ArrayList<>();
    		for(T element : elements) result.add(element);
    	return result;
    }
    
    public static String getCompleteName(Person p) {
    	return String.format("%s (%s)", p.getName(), p.getCredentials().getUsername());
    }
}
