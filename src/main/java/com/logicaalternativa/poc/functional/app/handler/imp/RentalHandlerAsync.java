package com.logicaalternativa.poc.functional.app.handler.imp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.FunctionMapper;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerAsync;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;

public class RentalHandlerAsync implements IRentalHandlerAsync {
	
	private static Logger logger = LoggerFactory.getLogger( RentalHandlerAsync.class.getSimpleName()  );
	
	private  IRentalRepositoryAsync repository;
	
	
	public RentalHandlerAsync(final IRentalRepositoryAsync repository) {
		super();
		this.repository = repository;
	}


	@Override
	public AlternativeFuture<RentalDto> create(final CommandCreate comm) {
		
		logger.info("CommandCreate: "  + comm) ;
		
		final RentalDto rentalDto = new RentalDto(null, comm.getIdFlim(), comm.getIdCustomer());
		
		final AlternativeFuture<IRentalAggregate> aggregateFuture = repository.create( rentalDto );
		
		final ExecutorService executor = Executors.newCachedThreadPool();
		
		return aggregateFuture
			.flatMap( new FunctionMapper<IRentalAggregate, AlternativeFuture<IRentalAggregate>>() {

						@Override
						public AlternativeFuture<IRentalAggregate> map(IRentalAggregate aggregate) {
							
							if ( aggregate.validateBeforeSave() ) {
								
								return repository.save( aggregate );
								
							}
							
							throw new RuntimeException( "validate entity is not OK" );
						}
					}, executor )
			.map( new FunctionMapper<IRentalAggregate, RentalDto>() {

				@Override
				public RentalDto map(IRentalAggregate aggregate) {
					 
					return new RentalDto( aggregate.getId(), 
								aggregate.getIdFilm(), 
								aggregate.getIdCustomer()
								);
				}
			} , executor );
	}

}
