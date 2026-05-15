package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class Cash implements IPayment{

	@Override
	public String pay() {
		return "Payment type Cash, Payment Successful... ";
	}

}