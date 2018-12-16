package com.rpl.runner.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpl.model.runner.Tests;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();


    public static String objectToJson(Object o) throws JsonProcessingException{
        return mapper.writeValueAsString(o);
    }

    public static Tests stringToObject(String json) {
        try {
            mapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
            return mapper.readValue(json, Tests.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
