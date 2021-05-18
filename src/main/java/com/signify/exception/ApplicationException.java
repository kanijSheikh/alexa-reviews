package com.signify.exception;

public class ApplicationException  extends RuntimeException{

    private String message;

    public ApplicationException(String message) {
        this.message = message;
    }

    public ApplicationException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
    }

    public ApplicationException(Throwable ex) {
        super(ex);
    }
}
