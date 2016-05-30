package com.logicaalternativa.poc.functional.app.handler.imp;

import static com.logicaalternativa.forcomprehensions.util.UtilFor.args;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.gBol;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.var;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor;
import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandler;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerFunctional;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;

public class RentalHandlerFunctionalJ8 implements IRentalHandlerFunctional<Monad<RentalDto>> {
	
	private static Logger logger = LoggerFactory.getLogger( RentalHandlerFunctionalJ8.class.getSimpleName()  );
	
	@SuppressWarnings("rawtypes")
	private ILanguageHandler lang;

	public RentalHandlerFunctionalJ8(final ILanguageHandler language) {
		super();
		this.lang = language;
	}

	@Override
	public Monad<RentalDto> create( final CommandCreate comm ) {
		
		logger.info("CommandCreate: "  + comm) ;
		
		final IFor interpreter = BuilderFor.getInstace();
		
		final RentalDto rentalDto = new RentalDto(null, comm.getIdFlim(), comm.getIdCustomer());
		
		@SuppressWarnings("unchecked")
		final Monad<RentalDto> rentalDtoMonad = interpreter
			.line(
					var("aggregate"),
					s -> lang.createEntity( gDto( s[0] ) ),
					args(rentalDto)
				)
			.line(
					var("resValidate"),
					s ->  lang.validateEntity( gAgr( s[0] ) ),
					args( var("aggregate") )
				)
			.line(
					var("aggregateRes"),
					s -> lang.saveEntity( gBol( s[0] ), gAgr( s[1] ) ) ,
					args( var("resValidate"), var("aggregate") )
				)
			.yield(
					s -> lang.transform( gAgr( s[0] ) ) , 
					args( var( "aggregateRes" ) )
				  );
		
		return  rentalDtoMonad;
		
	}
	
	private IRentalAggregate gAgr( Object object ) {
		
		return ( IRentalAggregate ) object;
		
	}
	
	private RentalDto gDto( Object object ) {
		
		return ( RentalDto ) object;
		
	}

}
