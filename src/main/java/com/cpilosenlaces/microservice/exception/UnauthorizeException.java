package com.cpilosenlaces.microservice.exception;

public class UnauthorizeException extends RuntimeException {
    private final static String DEFAULT_ERROR_MESSAGE = "Unauthorize exception";

    public UnauthorizeException(String message) {
        super(message);
    }

    public UnauthorizeException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
