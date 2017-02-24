package com.rpl.service.util;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <T> List<T> listOf(T... elements){
    	ArrayList<T> result = new ArrayList<>();
    		for(T element : elements) result.add(element);
    	return result;
    }
}
