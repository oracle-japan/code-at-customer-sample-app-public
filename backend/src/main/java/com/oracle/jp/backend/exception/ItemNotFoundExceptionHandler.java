package com.oracle.jp.backend.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemNotFoundExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .eTag(UUID.randomUUID().toString())
            .body(ErrorResponse.builder().httpStatus(HttpStatus.NOT_FOUND).message(e.getMessage()).build());
    }
}
