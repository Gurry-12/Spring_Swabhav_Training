package com.gurpreet.monocept.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleStudentNotFoundException(StudentNotFoundException ex) {

		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.NOT_FOUND.value());
		errorBody.put("error", "Not Found");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {

		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Bad Request");
		errorBody.put("message", "Invalid Id. Id must be a number");

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleTypeMismatchException(IllegalArgumentException ex) {

		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Bad Request");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {

		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorBody.put("error", "Internal Server Error");
		errorBody.put("message", "An unexpected error occurred");

		return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
