package com.gurpreet.monocept.model;

import org.springframework.stereotype.Component;

@Component
public class Card  implements IPayment{

	@Override
	public String pay() {
		return "Payment type Card, Payment Successful... ";
	}

}
