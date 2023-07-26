package com.workoutly.application.user.utils;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseValidator {

    private static MvcResult mvcResult;

    public static void setResult(MvcResult result) {
        mvcResult = result;
    }

    public static int isOk() {
        return 200;
    }

    public static int isBadRequest() {
        return 400;
    }

    public static int isForbidden() { return 403; }

    public static void responseStatusIs(int status) {
        assertTrue(mvcResult.getResponse().getStatus() == status);
    }

    public static void responseContentIs(String value) throws UnsupportedEncodingException {
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println(value);
        System.out.println(responseContent);
        assertTrue(responseContent.contains(value));
    }

}
