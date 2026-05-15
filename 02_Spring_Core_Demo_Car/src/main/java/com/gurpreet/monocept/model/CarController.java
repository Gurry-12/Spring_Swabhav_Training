package com.gurpreet.monocept.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

	private IEngine engine;

	@Autowired
	public CarController(@Qualifier("petrolEngine") IEngine engine) {
		this.engine = engine;
	}
	
	@GetMapping("/startCar")
	public String getCarEngine() {
		return engine.startEngine() + "\n Car Started";
		
	}
	
	
}
