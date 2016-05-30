package com.logicaalternativa.poc.functional.app.dto;

import java.io.Serializable;

public class RentalDto implements Serializable{
	
	private static final long serialVersionUID = -6697210255663055285L;

	private String idDTO;
	
	private String idFilm;

	private String idCustomer;
	
	public RentalDto(String idDTO, String idFilm, String idCustomer) {
		super();
		this.idDTO = idDTO;
		this.idFilm = idFilm;
		this.idCustomer = idCustomer;
	}
	
	public String getIdDTO() {
		return idDTO;
	}

	public String getIdFilm() {
		return idFilm;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RentalDto [idDTO=").append(idDTO).append(", idFilm=")
				.append(idFilm).append(", idCustomer=").append(idCustomer)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result + ((idDTO == null) ? 0 : idDTO.hashCode());
		result = prime * result + ((idFilm == null) ? 0 : idFilm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalDto other = (RentalDto) obj;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (idDTO == null) {
			if (other.idDTO != null)
				return false;
		} else if (!idDTO.equals(other.idDTO))
			return false;
		if (idFilm == null) {
			if (other.idFilm != null)
				return false;
		} else if (!idFilm.equals(other.idFilm))
			return false;
		return true;
	}
		

}
