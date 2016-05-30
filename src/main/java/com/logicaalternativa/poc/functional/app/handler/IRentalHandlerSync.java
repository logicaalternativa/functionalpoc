package com.logicaalternativa.poc.functional.app.handler;

import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;

public interface IRentalHandlerSync {
	
	RentalDto create( CommandCreate commandCreate );	

}
