package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class DieselEngine implements IEngine {

	@Override
	public String startEngine() {
		return "Diesel Engine Started...";
 	}

}
