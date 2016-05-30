package com.logicaalternativa.poc.functional.app.handler.imp;

import static com.logicaalternativa.forcomprehensions.util.UtilFor.args;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.function;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.invoke;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.mapper;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.var;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor.Type;
import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandler;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerFunctional;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;

public class RentalHandlerFunctional implements IRentalHandlerFunctional <Monad<RentalDto>>{
	
	private static Logger logger = LoggerFactory.getLogger( RentalHandlerFunctional.class.getSimpleName()  );
	
	@SuppressWarnings("rawtypes")
	private ILanguageHandler language;

	public RentalHandlerFunctional(final ILanguageHandler language) {
		super();
		this.language = language;
	}

	@Override
	public Monad<RentalDto> create( final CommandCreate comm ) {
		
		logger.info("CommandCreate: "  + comm) ;
		
		final IFor interpreter = BuilderFor.getInstance( Type.PARALLEL);
		
		final RentalDto rentalDto = new RentalDto(null, comm.getIdFlim(), comm.getIdCustomer());
		
		final Monad<RentalDto> rentalDtoMonad = interpreter
			.line(
					var("aggregate"),
					function( IRentalAggregate.class, invoke( language, "createEntity" ) ),
					args( rentalDto )
				)
			.line(
					var("resValidate"),
					function( Boolean.class, invoke( language, "validateEntity" ) ),
					args( var("aggregate") )
				)
			.line(
					var("aggregateRes"),
					function( IRentalAggregate.class, invoke( language, "saveEntity" ) ),
					args( var("resValidate"), var("aggregate") )
				)
			.yield(
					mapper(RentalDto.class, invoke( language, "transform" )  ), 
					args( var( "aggregateRes" ) )
				  );
		
		return rentalDtoMonad;
		
	}

}
