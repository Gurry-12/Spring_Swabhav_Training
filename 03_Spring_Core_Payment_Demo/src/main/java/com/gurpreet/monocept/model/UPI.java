package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class UPI implements IPayment{

	@Override
	public String pay() {
		return "Payment type UPI, Payment Successful... ";
	}

}