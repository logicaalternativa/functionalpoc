package com.logicaalternativa.poc.functional.app.handler.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerSync;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositorySync;

public class RentalHandlerSync implements IRentalHandlerSync {
	
	private static Logger logger = LoggerFactory.getLogger( RentalHandlerSync.class.getSimpleName()  );

	private  IRentalRepositorySync repository;
	
	public RentalHandlerSync(final IRentalRepositorySync repository) {
		super();
		this.repository = repository;
	}


	@Override
	public RentalDto create(final CommandCreate comm) {
		
		logger.info("CommandCreate: "  + comm) ;
		
		final RentalDto rentalDto = new RentalDto(null, comm.getIdFlim(), comm.getIdCustomer());
		
		IRentalAggregate aggregate = repository.create( rentalDto );
		
		final Boolean res = aggregate.validateBeforeSave();
		
		if (  res  ) {
			
			 aggregate = repository.save( aggregate );
			 
			 return new RentalDto( aggregate.getId(), 
 										aggregate.getIdFilm(), 
 										aggregate.getIdCustomer()
 										);
			
		}
		
		return null;
	}

}
