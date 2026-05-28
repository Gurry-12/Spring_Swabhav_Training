package com.gurpreet.monocept.exception;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(int id) {
		super("Employee not found at id : " + id);
	}
}
