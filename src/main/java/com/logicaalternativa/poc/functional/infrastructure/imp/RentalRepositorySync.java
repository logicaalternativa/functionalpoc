package com.logicaalternativa.poc.functional.infrastructure.imp;

import java.util.UUID;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.domain.ICustomerEntity;
import com.logicaalternativa.poc.functional.domain.IFilmEntity;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositorySync;
import com.logicaalternativa.poc.functional.domain.imo.CustomerEntity;
import com.logicaalternativa.poc.functional.domain.imo.FilmEntity;
import com.logicaalternativa.poc.functional.domain.imo.RentalAggregate;

public class RentalRepositorySync implements IRentalRepositorySync {	
	
	private static Logger logger = LoggerFactory.getLogger( RentalRepositorySync.class.getSimpleName() );

	private <T> T createActiveObjectEntity( T implementation, Class<T> interfaze ) {
		
	  return BuilderActiveObject
					.getInstance(interfaze )
					.withExecutor( Executors.newSingleThreadExecutor())
					.withTimeoutMiliSecIfLocks( 2000L )
					.withImplementation( implementation )
					.build();
	}

	@Override
	public IRentalAggregate create(RentalDto dto) {
		
		logger.info("RentalDto: " + dto) ;
		
		final IFilmEntity filmEntity = createActiveObjectEntity(new FilmEntity( dto.getIdFilm(), false), IFilmEntity.class );
		
		final ICustomerEntity customerEntity = createActiveObjectEntity(new CustomerEntity( dto.getIdCustomer(), true, "" ), ICustomerEntity.class );
		
		final RentalAggregate rentalAggregate = new RentalAggregate( null, filmEntity, customerEntity);
		
		return createActiveObjectEntity( rentalAggregate, IRentalAggregate.class ); 
	}

	@Override
	public IRentalAggregate save(IRentalAggregate aggregate) {
		
		logger.info(" IRentalAggregate: " + aggregate) ;
		
		if ( aggregate != null  ) {
			
			aggregate.setId( UUID.randomUUID().toString() );
			
		}
			
				
		return aggregate;
	}

}
