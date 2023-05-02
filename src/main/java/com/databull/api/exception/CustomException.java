package com.databull.api.exception;

public class CustomException extends RuntimeException {
    private ErrorMessage errorMessage;
    public CustomException() {}

    public CustomException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage.toString(), cause);
    }

    public CustomException(String errorMessage) {
        super(errorMessage);
    }

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }

    public static class ErrorMessage {
        String message;
        Long statusCode;

        public ErrorMessage(String message, long statusCode) {
            this.statusCode = statusCode;
            this.message = message;
        }

        @Override
        public String toString() {
            return "{statusCode=" + this.statusCode + ", message=" + this.message + "}";
        }
    }
}
