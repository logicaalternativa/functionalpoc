package com.logicaalternativa.poc.functional.app.command;

import java.io.Serializable;

public class CommandCreate implements Serializable {

	private static final long serialVersionUID = -3581626138951779283L;
	
	private String idFlim;
	private String idCustomer;

	public CommandCreate(String idFlim, String idCustomer) {
		this.idFlim = idFlim;
		this.idCustomer = idCustomer;
	}

	public String getIdFlim() {
		return idFlim;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommandCreate [idFlim=").append(idFlim)
				.append(", idCustomer=").append(idCustomer).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result + ((idFlim == null) ? 0 : idFlim.hashCode());
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
		CommandCreate other = (CommandCreate) obj;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (idFlim == null) {
			if (other.idFlim != null)
				return false;
		} else if (!idFlim.equals(other.idFlim))
			return false;
		return true;
	}
	
	

}
