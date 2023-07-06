package com.unforgettable.testtask.handler;

import com.unforgettable.testtask.exception.ArticleException;
import com.unforgettable.testtask.exception.AuthorException;
import com.unforgettable.testtask.exception.SiteException;
import com.unforgettable.testtask.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                      HttpServletRequest request) {
        HttpStatus httpStatus = BAD_REQUEST;
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .httpStatus(httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {SiteException.class})
    public ResponseEntity<ExceptionResponse> handleSiteFoundException(SiteException exception,
                                                                      HttpServletRequest request) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .httpStatus(httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ArticleException.class})
    public ResponseEntity<ExceptionResponse> handleArticleException(ArticleException exception,
                                                                    HttpServletRequest request) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .httpStatus(httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {AuthorException.class})
    public ResponseEntity<ExceptionResponse> handleAuthorException(AuthorException exception,
                                                                   HttpServletRequest request) {
        HttpStatus httpStatus = exception.getHttpStatus();
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .httpStatus(httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ConstraintViolationException .class})
    public ResponseEntity<ExceptionResponse> handleValidationException(ConstraintViolationException exception,
                                                                       HttpServletRequest request) {
        HttpStatus httpStatus = BAD_REQUEST;
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String message = null;
        for (ConstraintViolation<?> violation : violations) {
            message = violation.getMessage();
            break;
        }
        ExceptionResponse response = ExceptionResponse.builder()
                .message(message)
                .httpStatus(httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

}
