import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerSync;
import com.logicaalternativa.poc.functional.app.handler.imp.RentalHandlerSync;
import com.logicaalternativa.poc.functional.domain.IRentalRepositorySync;
import com.logicaalternativa.poc.functional.infrastructure.imp.RentalRepositorySync;


public class MainSycn {
	
	private static Logger logger = LoggerFactory.getLogger( MainSycn.class.getSimpleName()  );
	
	private static <T> T createActiveObject( T implementation, Class<T> interfaze ) {
		
		  return BuilderActiveObject
						.getInstance(interfaze )
						.withExecutor( Executors.newCachedThreadPool())
						.withTimeoutMiliSecIfLocks( 2000L )
						.withImplementation( implementation )
						.build();
		}
	

	public static void main(String[] args) {
		
		final IRentalRepositorySync repositorySync = createActiveObject(new RentalRepositorySync(), IRentalRepositorySync.class ); 
		
		final IRentalHandlerSync handler = createActiveObject(new RentalHandlerSync( repositorySync ), IRentalHandlerSync.class);
		
		final String idFlim = "idFlim";		
		final String idCustomer = "idCustomer";
		
		final CommandCreate commandCreate = new CommandCreate( idFlim , idCustomer );
		
		final RentalDto rentalDto = handler.create( commandCreate );
		
		logger.info(" RENTAL DTO " + rentalDto) ;
		
		System.exit(0);		
		

	}

}
