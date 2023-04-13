package com.example.demo.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.front.MockController.IdNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class MyExceptionHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("ERROR! " + ex);
        if(ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(ex instanceof IdNotFoundException) {
            IdNotFoundException e = (IdNotFoundException)ex;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
        if(ex instanceof HttpClientErrorException){
            HttpClientErrorException e = (HttpClientErrorException)ex;
            return ResponseEntity.status(e.getStatusCode()).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
        if(ex instanceof RestClientException){
            RestClientException e = (RestClientException)ex;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
        if(ex instanceof ResponseStatusException){
            ResponseStatusException e = (ResponseStatusException)ex;
            return ResponseEntity.status(e.getStatus()).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}