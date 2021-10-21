package com.demo.shortenurl.controller;

import com.demo.shortenurl.exception.InvalidRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvisor {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Void> invalidRequestException(InvalidRequestException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unhandledException(Exception exception) {
        return ResponseEntity.internalServerError().build();
    }
}
