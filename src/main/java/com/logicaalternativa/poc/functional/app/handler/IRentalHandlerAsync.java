package com.logicaalternativa.poc.functional.app.handler;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;


public interface IRentalHandlerAsync {
	
	AlternativeFuture<RentalDto> create( CommandCreate commandCreate );	
	
		

}
