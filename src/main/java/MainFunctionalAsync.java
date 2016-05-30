import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.futures.imp.AwaitAlternativeFuture;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandler;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandlerFuture;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerAsync;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerFunctional;
import com.logicaalternativa.poc.functional.app.handler.imp.LanguageHandlerFuture;
import com.logicaalternativa.poc.functional.app.handler.imp.RentalHandlerFunctionalJ8;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;
import com.logicaalternativa.poc.functional.infrastructure.ILanguageRentalRepositoryAsyn;
import com.logicaalternativa.poc.functional.infrastructure.IRestClientAsync;
import com.logicaalternativa.poc.functional.infrastructure.imp.LanguageRentalRepositoryAsyn;
import com.logicaalternativa.poc.functional.infrastructure.imp.RentalRepositoryFuntional;
import com.logicaalternativa.poc.functional.infrastructure.imp.RestClientAsync;


public class MainFunctionalAsync {
	
	private static Logger logger = LoggerFactory.getLogger( MainFunctionalAsync.class.getSimpleName()  );
	
	private static <T> T createActiveObject( T implementation, Class<T> interfaze ) {
		
		  return BuilderActiveObject
						.getInstance(interfaze )
						.withExecutor( Executors.newFixedThreadPool(20))
						.withTimeoutMiliSecIfLocks( 2000L )
						.withImplementation( implementation )
						.build();
		}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		
		/* CONFIG */
		
		final IRestClientAsync restClientAsync = createActiveObject( new RestClientAsync() , IRestClientAsync.class);
		
		final ILanguageRentalRepositoryAsyn languageRepo =  createActiveObject(new LanguageRentalRepositoryAsyn( restClientAsync  ), ILanguageRentalRepositoryAsyn.class );
		
		final IRentalRepositoryAsync repositoryAsync = createActiveObject(new RentalRepositoryFuntional<>( languageRepo ), IRentalRepositoryAsync.class );
		
		final ILanguageHandler language = (ILanguageHandler) createActiveObject( new LanguageHandlerFuture( repositoryAsync ), ILanguageHandlerFuture.class);
		
		IRentalHandlerFunctional<Monad<RentalDto>> implHandler = new RentalHandlerFunctionalJ8( language );
//		IRentalHandlerFunctional<Monad<RentalDto>> implHandler = new RentalHandlerFunctional( language );
		
		final IRentalHandlerFunctional<Monad<RentalDto>> handler = createActiveObject( implHandler, IRentalHandlerFunctional.class);
		
		/* CALL */
		
		final String idFlim = "idFlim";		
		final String idCustomer = "idCustomer";
		
		final CommandCreate commandCreate = new CommandCreate( idFlim , idCustomer );
		
		final AlternativeFuture<RentalDto> rentalDtoFuture = (AlternativeFuture<RentalDto>) handler.create( commandCreate );
		
		RentalDto rentalDto = AwaitAlternativeFuture.result( rentalDtoFuture, 5000L );
		
		logger.info(" RENTAL DTO " + rentalDto) ;
		
		System.exit(0);		
		

	}

}
