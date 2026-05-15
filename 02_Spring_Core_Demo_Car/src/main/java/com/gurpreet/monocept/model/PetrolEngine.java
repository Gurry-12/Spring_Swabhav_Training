package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class PetrolEngine implements IEngine {

	@Override
	public String startEngine() {
		return "Petrol Engine Started...";
	}

}
