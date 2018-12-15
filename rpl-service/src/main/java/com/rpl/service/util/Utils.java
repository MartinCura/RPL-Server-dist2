package com.rpl.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.rpl.model.Person;

public class Utils {

    @SafeVarargs
    public static <T> List<T> listOf(T... elements){
        return new ArrayList<>(Arrays.asList(elements));
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

    public static Date addMonths (Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
}
