package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class BadmintonCoach implements ICoach {

	@Override
	public String getWorkoutDetails() {
		return "Badminton - Practice smash in badminton";
	}

	/**
	 * 
	 */
	public BadmintonCoach() {
		System.out.println("Inside - " + getClass().getSimpleName());
	}

}
