package com.logicaalternativa.poc.functional.app.handler;

import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;

public interface  ILanguageHandler <T extends Monad<IRentalAggregate>, S extends Monad<Boolean>> {
	
	T createEntity(final RentalDto rentalDto);
	
	S validateEntity( final IRentalAggregate rentalAggregate );
	
	T saveEntity( final Boolean resValidation, final IRentalAggregate rentalAggregate );
	
	RentalDto transform( final IRentalAggregate rentalAggregate  );	

}
