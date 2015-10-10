package com.dch.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by dcherdyntsev on 05.08.2015.
 */
public class JsonHelper {

    private ObjectMapper mapper = new ObjectMapper();

    public String toJsonString(Object o) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, o);
        return writer.toString();
    }

    public <T> T fromJsonString(String jsonString, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonString, clazz);
    }

}
