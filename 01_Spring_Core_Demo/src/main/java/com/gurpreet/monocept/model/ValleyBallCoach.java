package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class ValleyBallCoach implements ICoach{

	@Override
	public String getWorkoutDetails() {
		return "Valley Ball - Practice Smash and passing";
	}

	public ValleyBallCoach() {
		System.out.println("Inside - " + getClass().getSimpleName());
	}

}
