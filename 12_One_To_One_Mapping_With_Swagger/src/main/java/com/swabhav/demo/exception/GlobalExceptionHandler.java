package com.swabhav.demo.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.NOT_FOUND.value());
		errorBody.put("error", "Not Found");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateResource(DuplicateResourceException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.CONFLICT.value());
		errorBody.put("error", "Conflict");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Bad Request");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Bad Request");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Bad Request");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.CONFLICT.value());
		errorBody.put("error", "Conflict");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.FORBIDDEN.value());
		errorBody.put("error", "Forbidden");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
		Map<String, Object> errorBody = new HashMap<>();

		errorBody.put("Timestamp", LocalDateTime.now());
		errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorBody.put("error", "Internal server error");
		errorBody.put("message", ex.getMessage());

		return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
