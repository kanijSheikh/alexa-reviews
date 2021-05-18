package com.signify.interceptor;


import com.signify.exception.ApplicationException;
import com.signify.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApplicationException.class)
    protected ResponseEntity<Response> handleConflict(ApplicationException ex) {

        return new ResponseEntity<>(new Response(ex.getMessage()), null, HttpStatus.NOT_FOUND);
    }
}
