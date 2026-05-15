package com.gurpreet.monocept.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CricketCoach  implements ICoach{

	@Override
	public String getWorkoutDetails() {
		return "Cricket - Practice Batting and Bowling";
	}

	public CricketCoach() {
		System.out.println("Inside - " + getClass().getSimpleName());
	}
	

}
