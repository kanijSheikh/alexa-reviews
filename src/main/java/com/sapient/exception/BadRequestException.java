package com.sapient.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super("The requested URL could not be processed because of bad request");
    }
}