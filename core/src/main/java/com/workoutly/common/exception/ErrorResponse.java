package com.workoutly.common.exception;

public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponseBuilder anErrorResponse() {
        return new ErrorResponseBuilder();
    }

    public static final class ErrorResponseBuilder {
        private String code;
        private String message;

        private ErrorResponseBuilder() {
        }

        public ErrorResponseBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(code, message);
        }
    }
}
