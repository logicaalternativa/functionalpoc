package com.logicaalternativa.poc.functional.app.handler;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;

public interface ILanguageHandlerFuture extends ILanguageHandler<AlternativeFuture<IRentalAggregate>, AlternativeFuture<Boolean>>{
	
	AlternativeFuture<IRentalAggregate> createEntity(final RentalDto rentalDto);
	
	AlternativeFuture<Boolean> validateEntity( final IRentalAggregate rentalAggregate );
	
	AlternativeFuture<IRentalAggregate> saveEntity( final Boolean resValidation, final IRentalAggregate rentalAggregate );

}
