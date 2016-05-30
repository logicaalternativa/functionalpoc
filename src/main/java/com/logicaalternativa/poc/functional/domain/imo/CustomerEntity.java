package com.logicaalternativa.poc.functional.domain.imo;

import com.logicaalternativa.poc.functional.domain.ICustomerEntity;

public class CustomerEntity implements ICustomerEntity {	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerEntity [id=").append(id).append(", isActive=")
				.append(isActive).append(", creditCart=").append(creditCart)
				.append("]");
		return builder.toString();
	}

	private String id;
	
	private Boolean isActive;

	private String creditCart;

	public CustomerEntity(String id, Boolean isActive, String creditCart) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.creditCart = creditCart;
	}

	public String getCreditCart() {
		return creditCart;
	}

	public void setCreditCart(String creditCart) {
		this.creditCart = creditCart;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean isAnActiveCustomer() {
		
		return isActive && creditCart != null;
	}

}
