package com.rpl.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
	
	public static String objectToJson(Object o) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(o);
	}

	//QUI
	public static <T> T jsonToObject(String s, Class<T> oClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(s, oClass);
	}
}
