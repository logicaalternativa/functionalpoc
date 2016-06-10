import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.imp.AwaitAlternativeFuture;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerAsync;
import com.logicaalternativa.poc.functional.app.handler.imp.RentalHandlerAsync;
import com.logicaalternativa.poc.functional.domain.IRentalRepositoryAsync;
import com.logicaalternativa.poc.functional.infrastructure.IRestClientAsync;
import com.logicaalternativa.poc.functional.infrastructure.imp.RentalRepositoryAsync;
import com.logicaalternativa.poc.functional.infrastructure.imp.RestClientAsync;


public class MainAsycn {
	
	private static Logger logger = LoggerFactory.getLogger( MainAsycn.class.getSimpleName()  );
	
	private static <T> T createActiveObject( T implementation, Class<T> interfaze ) {
		
		  return BuilderActiveObject
						.getInstance(interfaze )
						.withExecutor( Executors.newCachedThreadPool())
						.withTimeoutMiliSecIfLocks( 2000L )
						.withImplementation( implementation )
						.build();
		}
	
	private static IRentalHandlerAsync configure() {
		final IRestClientAsync restClientAsync = createActiveObject( new RestClientAsync() , IRestClientAsync.class);
		
		final IRentalRepositoryAsync repositoryAsync = createActiveObject(new RentalRepositoryAsync( restClientAsync ), IRentalRepositoryAsync.class ); 
		
		final IRentalHandlerAsync handler = createActiveObject(new RentalHandlerAsync( repositoryAsync ), IRentalHandlerAsync.class);
		return handler;
	}
	

	public static void main(String[] args) throws Exception {
		
		final IRentalHandlerAsync handler = configure();
		
		// ---------------------------------------
		// ---------------------------------------
		// ---------------------------------------
		// ---------------------------------------
		
		final String idFlim = "idFlim";		
		final String idCustomer = "idCustomer";
		
		final CommandCreate commandCreate = new CommandCreate( idFlim , idCustomer );
		
		final AlternativeFuture<RentalDto> rentalDtoFuture = handler.create( commandCreate );
		
		RentalDto rentalDto = AwaitAlternativeFuture.result(rentalDtoFuture, 5000L);
		
		logger.info(" RENTAL DTO " + rentalDto) ;
		
		System.exit(0);		
		

	}


	

}
