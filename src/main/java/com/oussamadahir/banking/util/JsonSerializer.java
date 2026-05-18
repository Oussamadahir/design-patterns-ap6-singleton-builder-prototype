package com.oussamadahir.banking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> {
    private final ObjectMapper mapper = new ObjectMapper();

    public String toJson(T dataObject) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
