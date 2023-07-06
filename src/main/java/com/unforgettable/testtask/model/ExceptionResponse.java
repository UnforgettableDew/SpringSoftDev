package com.unforgettable.testtask.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@Builder
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private Timestamp timestamp;
    private String path;
}
