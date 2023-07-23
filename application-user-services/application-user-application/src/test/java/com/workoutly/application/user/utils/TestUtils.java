package com.workoutly.application.user.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
