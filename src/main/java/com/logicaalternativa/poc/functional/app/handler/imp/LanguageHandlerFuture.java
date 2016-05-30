package com.logicaalternativa.poc.functional.app.handler.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandlerFuture;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;

public class LanguageHandlerFuture implements ILanguageHandlerFuture {
	
	private static Logger logger = LoggerFactory.getLogger( LanguageHandlerFuture.class.getSimpleName()  );

	private final IRentalRepositoryAsync repository;
	
	public LanguageHandlerFuture( IRentalRepositoryAsync repository ) {
		super();
		this.repository = repository;
	}

	@Override
	public AlternativeFuture<IRentalAggregate> createEntity( RentalDto rentalDto ) {
		
		logger.info("RentalDto: " + rentalDto) ;
		
		final AlternativeFuture<IRentalAggregate> aggregateFuture = repository.create( rentalDto );
		
		return aggregateFuture;
	}

	@Override
	public AlternativeFuture<Boolean> validateEntity(
			IRentalAggregate rentalAggregate) {
		
		logger.info("IRentalAggregate: " + rentalAggregate ) ;
		
		final Boolean validateBeforeSave = rentalAggregate.validateBeforeSave();
		
		return AlternativeFuture.successful( validateBeforeSave );
	}

	@Override
	public AlternativeFuture<IRentalAggregate> saveEntity(
			final Boolean resValidation,			
			final IRentalAggregate rentalAggregate) {
		
		logger.info("resValidation: '" + resValidation + "', IRentalAggregate: " + rentalAggregate) ;
		
		if ( resValidation ) {
			
			return repository.save( rentalAggregate );
			
		}
		
		return AlternativeFuture.failed( new RuntimeException( "validate entity is not OK" ) );		
		
	}

	@Override
	public RentalDto transform( final IRentalAggregate aggregate) {
		
		return new RentalDto( aggregate.getId(), 
				aggregate.getIdFilm(), 
				aggregate.getIdCustomer()
				);
		
	}

	
}
