package com.unforgettable.testtask.exception;

import org.springframework.http.HttpStatus;

public class AuthorException extends RuntimeException{
    private final HttpStatus httpStatus;

    public AuthorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
