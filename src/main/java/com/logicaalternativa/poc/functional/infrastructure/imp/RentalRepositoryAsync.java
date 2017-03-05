package com.logicaalternativa.poc.functional.infrastructure.imp;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.pojo.AlternativeTuple;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.domain.ICustomerEntity;
import com.logicaalternativa.poc.functional.domain.IFilmEntity;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;
import com.logicaalternativa.poc.functional.domain.imo.CustomerEntity;
import com.logicaalternativa.poc.functional.domain.imo.FilmEntity;
import com.logicaalternativa.poc.functional.domain.imo.RentalAggregate;
import com.logicaalternativa.poc.functional.infrastructure.IRestClientAsync;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public class RentalRepositoryAsync implements IRentalRepositoryAsync{
	
	private static Logger logger = LoggerFactory.getLogger( RentalRepositoryAsync.class.getSimpleName()  );

	private IRestClientAsync iRestClientAsync;
	
	public RentalRepositoryAsync(IRestClientAsync iRestClientAsync) {
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
	public AlternativeFuture<IRentalAggregate> create(final RentalDto dto) {
	
		// return createJ7( dto );
		
		return createJ8( dto );
		
	}
	
	public AlternativeFuture<IRentalAggregate> createJ7(final RentalDto dto) {
		
		logger.info("RentalDto: " + dto) ;
		
		final ExecutorService executor = Executors.newCachedThreadPool();
		
		final AlternativeFuture<CustomerDto> customerFuture = iRestClientAsync.getCustomer( dto.getIdCustomer() );
		
		final AlternativeFuture<FilmDto> filmFuture = iRestClientAsync.getFilm( dto.getIdFilm() );
		
		final AlternativeFuture<CustomerInfoDto> customerInfoFuture = customerFuture
				.flatMap(
						new Function<CustomerDto, AlternativeFuture<CustomerInfoDto>>() {

							@Override
							public AlternativeFuture<CustomerInfoDto> apply(CustomerDto arg0) {
								
								if ( arg0.getIsVip() ) {
									
									return iRestClientAsync.getExtraInfoVip( arg0.getId() );
									
								}
								
								return iRestClientAsync.getExtraInfo( arg0.getId() );
								
							}				
							
						}
						
				, executor);
				
		
		AlternativeFuture<AlternativeTuple<CustomerDto, FilmDto>> zipFuture0 = customerFuture.zip( filmFuture );
		
		AlternativeFuture<AlternativeTuple<AlternativeTuple<CustomerDto, FilmDto>, CustomerInfoDto>> zipFuture = zipFuture0.zip( customerInfoFuture );
		
		
		return zipFuture.map( 
				
				new Function<AlternativeTuple<AlternativeTuple<CustomerDto,FilmDto>,CustomerInfoDto>, IRentalAggregate>() {

					@Override
					public IRentalAggregate apply(
							AlternativeTuple<AlternativeTuple<CustomerDto, FilmDto>, CustomerInfoDto> tuple) {
						
						final FilmDto filmDto = tuple.getA().getB();
						
						final CustomerDto customerDto = tuple.getA().getA();
						
						final CustomerInfoDto customerInfoDto = tuple.getB();
						
						final FilmEntity filmEntityImp = new FilmEntity(filmDto.getId(), filmDto.getIsDiscontinued() );

						final IFilmEntity filmEntity = createActiveObjectEntity( filmEntityImp, IFilmEntity.class );
						
						final CustomerEntity customerEntityImp = new CustomerEntity( customerDto.getId(), customerDto.getIsActive(), customerInfoDto.getCardNumber() );
						
						final ICustomerEntity customerEntity = createActiveObjectEntity( customerEntityImp, ICustomerEntity.class );

						final RentalAggregate rentalAggregate = new RentalAggregate( null, filmEntity, customerEntity);
						
						return createActiveObjectEntity(rentalAggregate, IRentalAggregate.class );

					}
					
					
				}, executor );
		
	}
	
	public AlternativeFuture<IRentalAggregate> createJ8(final RentalDto dto) {
		
		logger.info(" .. .. Calling Method createJ8") ;
		
		final ExecutorService executor = Executors.newCachedThreadPool();
		
		final AlternativeFuture<CustomerDto> customerFuture = iRestClientAsync.getCustomer( dto.getIdCustomer() );
		
		final AlternativeFuture<FilmDto> filmFuture = iRestClientAsync.getFilm( dto.getIdFilm() );
		
		final AlternativeFuture<CustomerInfoDto> customerInfoFuture = customerFuture
				.flatMap(
						s -> {
								
								if ( s.getIsVip() ) {
									
									return iRestClientAsync.getExtraInfoVip( s.getId() );
									
								}
								
								return iRestClientAsync.getExtraInfo( s.getId() );
								
							}						
				, executor);
				
		
		AlternativeFuture<AlternativeTuple<CustomerDto, FilmDto>> zipFuture0 = customerFuture.zip( filmFuture );
		
		AlternativeFuture<AlternativeTuple<AlternativeTuple<CustomerDto, FilmDto>, CustomerInfoDto>> zipFuture = zipFuture0.zip( customerInfoFuture );
		
		
		return zipFuture.map(
				s -> {
						
						final FilmDto filmDto = s.getA().getB();
						
						final CustomerDto customerDto = s.getA().getA();
						
						final CustomerInfoDto customerInfoDto = s.getB();
						
						final FilmEntity filmEntityImp = new FilmEntity(filmDto.getId(), filmDto.getIsDiscontinued() );

						final IFilmEntity filmEntity = createActiveObjectEntity( filmEntityImp, IFilmEntity.class );
						
						final CustomerEntity customerEntityImp = new CustomerEntity( customerDto.getId(), customerDto.getIsActive(), customerInfoDto.getCardNumber() );
						
						final ICustomerEntity customerEntity = createActiveObjectEntity( customerEntityImp, ICustomerEntity.class );

						final RentalAggregate rentalAggregate = new RentalAggregate( null, filmEntity, customerEntity);
						
						return createActiveObjectEntity(rentalAggregate, IRentalAggregate.class );

					}, executor );
		
	}

	@Override
	public AlternativeFuture<IRentalAggregate> save(IRentalAggregate aggregate) {
		
		logger.info(" IRentalAggregate: " + aggregate) ;
		
		if ( aggregate != null  ) {
			
			aggregate.setId( UUID.randomUUID().toString() );
			
		}
			
				
		return AlternativeFuture.successful( aggregate );
	}

}
