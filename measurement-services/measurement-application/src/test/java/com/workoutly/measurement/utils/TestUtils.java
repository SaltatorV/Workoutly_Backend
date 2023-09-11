package com.workoutly.measurement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String mapToString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
