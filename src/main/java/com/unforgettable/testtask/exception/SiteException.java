package com.unforgettable.testtask.exception;

import org.springframework.http.HttpStatus;

public class SiteException extends RuntimeException{
    private final HttpStatus httpStatus;

    public SiteException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
