package com.logicaalternativa.poc.functional.infrastructure.dto;

import java.io.Serializable;

public class CustomerDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final String id;
	
	private final Boolean isActive;
	
	private final Boolean isVip;
	
	public CustomerDto(String id, Boolean isActive, Boolean isVip) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.isVip = isVip;
	}
	
	public Boolean getIsVip() {
		return isVip;
	}

	public String getId() {
		return id;
	}

	public Boolean getIsActive() {
		return isActive;
	}	

}
