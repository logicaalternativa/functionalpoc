package com.logicaalternativa.poc.functional.app.dto;

import java.io.Serializable;

public class CustomerInfoDto implements Serializable {
	
	private static final long serialVersionUID = -8040825276601886173L;
	
	private final String cardNumber;

	public CustomerInfoDto(String cardNumber) {
		super();
		this.cardNumber = cardNumber;
	}


	public String getCardNumber() {
		return cardNumber;
	}
	
	
}
