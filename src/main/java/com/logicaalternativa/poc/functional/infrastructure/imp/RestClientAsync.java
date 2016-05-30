package com.logicaalternativa.poc.functional.infrastructure.imp;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.infrastructure.IRestClientAsync;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public class RestClientAsync implements IRestClientAsync {
	
	private static Logger logger = LoggerFactory.getLogger( RestClientAsync.class.getSimpleName() );	

	@Override
	public AlternativeFuture<CustomerDto> getCustomer(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		waiting(200);
		
		logger.info( "After waiting 200 milisec" );
		
		final CustomerDto customerDto = new CustomerDto( idCustomer, true, true);

		return AlternativeFuture.successful( customerDto );
	}	
	
	@Override
	public AlternativeFuture<FilmDto> getFilm(String idFilm) {
		
//		waiting(2);
		
		logger.info( "Id Film: " + idFilm );
		
		final FilmDto filmDto = new FilmDto( idFilm, false );
		
		return AlternativeFuture.successful( filmDto );
	}

	@Override
	public AlternativeFuture<CustomerInfoDto> getExtraInfo(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		final CustomerInfoDto customerInfo = new CustomerInfoDto( "1234" );
		
		return AlternativeFuture.successful( customerInfo );
	}

	@Override
	public AlternativeFuture<CustomerInfoDto> getExtraInfoVip(String idCustomer) {
		
		logger.info( "Id customer: " + idCustomer );
		
		final CustomerInfoDto customerInfo = new CustomerInfoDto( "4321" );
		
		return AlternativeFuture.successful( customerInfo );
		
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
