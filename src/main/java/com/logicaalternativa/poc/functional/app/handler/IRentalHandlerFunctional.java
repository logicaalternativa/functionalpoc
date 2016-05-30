package com.logicaalternativa.poc.functional.app.handler;

import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;

public interface IRentalHandlerFunctional <T extends Monad<RentalDto>> {
	
	 T create( CommandCreate commandCreate );	

}
