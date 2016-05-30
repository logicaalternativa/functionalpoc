package com.logicaalternativa.poc.functional.domain.imo;

import com.logicaalternativa.poc.functional.domain.ICustomerEntity;
import com.logicaalternativa.poc.functional.domain.IFilmEntity;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;

public class RentalAggregate implements IRentalAggregate {
	
	

	private String id;
	private IFilmEntity film;	

	private ICustomerEntity customer;

	public RentalAggregate( final String id, final IFilmEntity filmEntity, final ICustomerEntity customerEntity ) {
		this.id = id;
		this.film = filmEntity;
		this.customer = customerEntity;
		
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getIdFilm() {
		return film != null ? film.getId() : null;
	}

	@Override
	public String getIdCustomer() {
		return customer  != null ? customer.getId() : null;
	}

	@Override
	public Boolean validateBeforeSave() {
		
		return customer.isAnActiveCustomer() && film.validate();
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RentalAggregate [id=").append(id).append(", film=")
				.append(film).append(", customer=").append(customer)
				.append("]");
		return builder.toString();
	}
	
	

}
