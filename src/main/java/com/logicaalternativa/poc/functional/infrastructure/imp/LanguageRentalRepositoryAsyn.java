package com.logicaalternativa.poc.functional.infrastructure.imp;

import java.util.Random;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.domain.ICustomerEntity;
import com.logicaalternativa.poc.functional.domain.IFilmEntity;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.imo.CustomerEntity;
import com.logicaalternativa.poc.functional.domain.imo.RentalAggregate;
import com.logicaalternativa.poc.functional.infrastructure.ILanguageRentalRepositoryAsyn;
import com.logicaalternativa.poc.functional.infrastructure.IRestClientAsync;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public class LanguageRentalRepositoryAsyn implements
		ILanguageRentalRepositoryAsyn {
	
	private static Logger logger = LoggerFactory.getLogger( LanguageRentalRepositoryAsyn.class.getSimpleName() );
	
	private IRestClientAsync iRestClientAsync;
	


	public LanguageRentalRepositoryAsyn(IRestClientAsync iRestClientAsync) {
		super();
		this.iRestClientAsync = iRestClientAsync;
	}
	
	private <T> T createActiveObjectEntity( T implementation, Class<T> interfaze ) {
		
		  return BuilderActiveObject
						.getInstance(interfaze )
						.withExecutor( Executors.newSingleThreadExecutor())
						.withTimeoutMiliSecIfLocks( 2000L )
						.withImplementation( implementation )
						.build();
		}

	@Override
	public AlternativeFuture<CustomerDto> getCustomer(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		return iRestClientAsync.getCustomer( idCustomer );
	}

	@Override
	public AlternativeFuture<FilmDto> getFilm(String idFilm) {
		
		logger.info( "Id film: " + idFilm );
		
		return iRestClientAsync.getFilm( idFilm );
	}

	@Override
	public AlternativeFuture<CustomerInfoDto> getExtraInfo(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		return iRestClientAsync.getExtraInfo( idCustomer );
	}

	@Override
	public AlternativeFuture<CustomerInfoDto> getExtraInfoVip(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		return iRestClientAsync.getExtraInfoVip( idCustomer );
	}

	@Override
	public IRentalAggregate createEntity( final IFilmEntity filmEntityImp , final CustomerEntity customerEntityImp  ) {
		
		logger.info( "IFilmEntity: " + filmEntityImp + ", " + "CustomerEntity: " + customerEntityImp  );
		
		final IFilmEntity filmEntity = createActiveObjectEntity( filmEntityImp, IFilmEntity.class );
		
		final ICustomerEntity customerEntity = createActiveObjectEntity( customerEntityImp, ICustomerEntity.class );

		final RentalAggregate rentalAggregate = new RentalAggregate( null, filmEntity, customerEntity);
		
		return createActiveObjectEntity(rentalAggregate, IRentalAggregate.class );
	}
	
	private void waiting(Integer sleep) {
		
		int slp = sleep != null ? sleep.intValue() :  ( new Random() ).nextInt( 200 );
		
		try {
			Thread.sleep(slp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
