package com.rpl.runner.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpl.runner.result.Tests;

import java.io.IOException;

public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();


	public static String objectToJson(Object o) throws JsonProcessingException{
		return mapper.writeValueAsString(o);
	}

	public static Tests stringToObject(String json) {
		try {
			Tests tests = mapper.readValue(json, Tests.class);
			return tests;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
