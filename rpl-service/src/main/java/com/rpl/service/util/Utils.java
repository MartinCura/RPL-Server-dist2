package com.rpl.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static Date stringToDate(String dateStr) {
        if (dateStr == null) {
            return new Date();
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }

    }
}
