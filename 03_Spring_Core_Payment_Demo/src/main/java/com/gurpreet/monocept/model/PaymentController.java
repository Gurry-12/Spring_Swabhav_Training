package com.gurpreet.monocept.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

	
	private IPayment payment;
	
	@Autowired
	public PaymentController(@Qualifier("cash") IPayment payment) {
		this.payment = payment;
	}
	
	@GetMapping("/payment") 
	public String doPayment() {
		return payment.pay();
	}
}
