package com.logicaalternativa.poc.functional.infrastructure.imp;

import static com.logicaalternativa.forcomprehensions.util.UtilFor.args;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.toCast;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.var;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor.Type;
import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;
import com.logicaalternativa.poc.functional.domain.imo.CustomerEntity;
import com.logicaalternativa.poc.functional.domain.imo.FilmEntity;
import com.logicaalternativa.poc.functional.infrastructure.ILanguageRentalRepository;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public class   RentalRepositoryFuntional <T extends Monad<CustomerDto>, S extends Monad<FilmDto>, U extends Monad<CustomerInfoDto>> implements IRentalRepositoryAsync{
	
	private static Logger logger = LoggerFactory.getLogger( RentalRepositoryFuntional.class.getSimpleName()  );

	private ILanguageRentalRepository<T, S, U> lang;
	
	public RentalRepositoryFuntional(ILanguageRentalRepository<T, S, U>  lang) {
		super();
		this.lang = lang;
		
	}
	

	@Override
	public AlternativeFuture<IRentalAggregate> create(final RentalDto dto) {
		
		logger.info("RentalDto: " + dto) ;
		
		IFor interpreter = BuilderFor.getInstance( Type.PARALLEL );
		
		final String idCustom = dto.getIdCustomer() ;

		Monad<IRentalAggregate> aggergateF = interpreter
			.line( 
					var("customerD"),
					s -> lang.getCustomer( idCustom ), 
					null
			 )
			.line(
					var("filmD" ), 
					s -> lang.getFilm( dto.getIdFilm() ), 
					null
			 )
			 .line(
					var("customerInfoD" ), 
					s -> {
							return gCustD( s[0] ).getIsVip()
								? lang.getExtraInfoVip( idCustom )
										: lang.getExtraInfo( idCustom );
						}, 
					args( var( "customerD" ) )
				 )
			.yield(
					s -> {
						
						final CustomerDto customD = gCustD( s[0] );
						final FilmDto filmD = gFilmD( s[1] );
						final CustomerInfoDto custInfD = getCustInfoD( s[2] );
						
						final FilmEntity filmEntImp = new FilmEntity(filmD.getId(), filmD.getIsDiscontinued() );
						final CustomerEntity customerEntImp = new CustomerEntity( customD.getId(), customD.getIsActive(), custInfD.getCardNumber() );

						return lang.createEntity(filmEntImp, customerEntImp);
						
						
					}, 
					args( var("customerD"), var("filmD" ), var("customerInfoD" )  ) 
			);
		
			return (AlternativeFuture<IRentalAggregate>) aggergateF;
	}


	@Override
	public AlternativeFuture<IRentalAggregate> save(IRentalAggregate aggregate) {
		
		logger.info(" IRentalAggregate: " + aggregate) ;
		
		if ( aggregate != null  ) {
			
			aggregate.setId( UUID.randomUUID().toString() );
			
		}
			
				
		return AlternativeFuture.successful( aggregate );
	}

	
	private CustomerInfoDto getCustInfoD(Object object) {
		
		return toCast(object, CustomerInfoDto.class );
	}
	
	private FilmDto gFilmD(Object object) {
		
		return toCast(object, FilmDto.class );
		
	}
	
	private CustomerDto gCustD( Object object ) {
		
		return toCast(object, CustomerDto.class );
		
	}

}
