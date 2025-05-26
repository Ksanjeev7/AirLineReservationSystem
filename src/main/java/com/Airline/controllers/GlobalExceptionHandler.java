package com.Airline.controllers;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Airline.DTO.ErrorResponse;
import com.Airline.Exceptions.MyCustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MyCustomException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleException(MyCustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
        		 HttpStatus.NOT_FOUND.value(),
        		 ex.getMessage(),
        		 LocalDateTime.now()
        		);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Access Denied");
        body.put("message", "You are not authorized to access this resource.");

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
