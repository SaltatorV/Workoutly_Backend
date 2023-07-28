package com.workoutly.application.user.utils;

import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

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

    public static int responseStatusIs() {
        return mvcResult.getResponse().getStatus();
    }

    public static String responseContentIs(){
        try {
            return mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
