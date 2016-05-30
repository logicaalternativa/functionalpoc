package com.logicaalternativa.poc.functional.domain.imo;

import com.logicaalternativa.poc.functional.domain.IFilmEntity;

public class FilmEntity implements IFilmEntity {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilmEntity [id=").append(id)
				.append(", isDiscontinued=").append(isDiscontinued).append("]");
		return builder.toString();
	}

	private String id;	
	
	private Boolean isDiscontinued;

	public FilmEntity(String id, Boolean isDiscontinued) {
		super();
		this.id = id;
		this.isDiscontinued = isDiscontinued;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean validate() {
		
		return ! isDiscontinued;
		
	}
	
	
	
	

}
