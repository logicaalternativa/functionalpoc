package com.logicaalternativa.poc.functional.app.handler.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.forcomprehensions.option.AlterOption;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandlerOption;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositorySync;

public class LanguageHandlerOption implements ILanguageHandlerOption {
	
	private static Logger logger = LoggerFactory.getLogger( LanguageHandlerOption.class.getSimpleName()  );	

	private final IRentalRepositorySync repository;
	
	public LanguageHandlerOption( IRentalRepositorySync repository ) {
		
		super();
		this.repository = repository;
		
	}

	@Override
	public AlterOption<IRentalAggregate> createEntity(RentalDto rentalDto) {
		
		logger.info(" .. . Calling Method createEntity") ;
		
		final IRentalAggregate aggregateFuture = repository.create( rentalDto );
		
		return AlterOption.some( aggregateFuture );
	}

	@Override
	public AlterOption<Boolean> validateEntity(
			IRentalAggregate rentalAggregate) {
		
		logger.info(" .. . Calling Method validateEntity IRentalAggregate "+ rentalAggregate +"") ;
		
		final Boolean validateBeforeSave = rentalAggregate.validateBeforeSave();
		
		return AlterOption.some( validateBeforeSave );
	}

	@Override
	public AlterOption<IRentalAggregate> saveEntity(
			final Boolean resValidation,			
			final IRentalAggregate rentalAggregate) {
		
		logger.info(" .. . Calling Method saveEntity: resValidation " + resValidation + ", IRentalAggregate "+ rentalAggregate +"") ;
		
		if ( resValidation ) {
			
			 final IRentalAggregate saveAggregate = repository.save( rentalAggregate );
			 
			 return AlterOption.some( saveAggregate );			
		}
		
		return AlterOption.none( IRentalAggregate.class );		
		
	}

	@Override
	public RentalDto transform( final IRentalAggregate aggregate) {
		
		return new RentalDto( aggregate.getId(), 
				aggregate.getIdFilm(), 
				aggregate.getIdCustomer()
				);
		
	}

	
}
