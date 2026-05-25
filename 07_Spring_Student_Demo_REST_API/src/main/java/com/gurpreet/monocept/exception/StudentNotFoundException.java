package com.gurpreet.monocept.exception;

public class StudentNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(int id) {
		super("Student not found at id : " + id);
	}

}
