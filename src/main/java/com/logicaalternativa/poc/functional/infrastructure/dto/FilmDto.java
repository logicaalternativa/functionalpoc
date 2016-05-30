package com.logicaalternativa.poc.functional.infrastructure.dto;

public class FilmDto {
	
	private final String id;
	
	private final Boolean isDiscontinued;

	public FilmDto(String id, Boolean isDiscontinued) {
		super();
		this.id = id;
		this.isDiscontinued = isDiscontinued;
	}

	public String getId() {
		return id;
	}

	public Boolean getIsDiscontinued() {
		return isDiscontinued;
	}
	

}
