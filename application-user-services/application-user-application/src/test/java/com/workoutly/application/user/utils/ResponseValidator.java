package com.workoutly.application.user.utils;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

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

    public static void responseContentIs(List<String> contentValues) {
        try {
            String responseContent = mvcResult.getResponse().getContentAsString();
            for (String value: contentValues) {
                assertTrue(responseContent.contains(value));
                responseContent.replace(value, "");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
