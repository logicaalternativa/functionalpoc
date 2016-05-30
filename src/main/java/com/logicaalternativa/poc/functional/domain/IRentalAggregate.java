package com.logicaalternativa.poc.functional.domain;

public interface IRentalAggregate {
	
	String getId();
	
	String getIdCustomer();
	
	String getIdFilm();
	
	Boolean validateBeforeSave();

	void setId(String string);

}
