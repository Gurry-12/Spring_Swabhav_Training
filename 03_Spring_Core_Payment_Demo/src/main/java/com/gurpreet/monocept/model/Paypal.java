package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class Paypal implements IPayment{

	@Override
	public String pay() {
		return "Payment type Paypal, Payment Successful... ";
	}

}