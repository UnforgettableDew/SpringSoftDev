package com.unforgettable.testtask.exception;

import org.springframework.http.HttpStatus;

public class ArticleException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ArticleException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
