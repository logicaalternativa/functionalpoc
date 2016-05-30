import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logicaalternativa.forcomprehensions.option.AlterOption;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;
import com.logicaalternativa.poc.functional.app.command.CommandCreate;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;
import com.logicaalternativa.poc.functional.app.handler.ILanguageHandlerOption;
import com.logicaalternativa.poc.functional.app.handler.IRentalHandlerFunctional;
import com.logicaalternativa.poc.functional.app.handler.imp.LanguageHandlerOption;
import com.logicaalternativa.poc.functional.app.handler.imp.RentalHandlerFunctional;
import com.logicaalternativa.poc.functional.domain.IRentalRepositorySync;
import com.logicaalternativa.poc.functional.infrastructure.imp.RentalRepositorySync;


public class MainFunctionalSync {
	
	private static Logger logger = LoggerFactory.getLogger( MainFunctionalSync.class.getSimpleName()  );
	
	private static <T> T createActiveObject( T implementation, Class<T> interfaze ) {
		
		  return BuilderActiveObject
						.getInstance(interfaze )
						.withExecutor( Executors.newCachedThreadPool())
						.withTimeoutMiliSecIfLocks( 2000L )
						.withImplementation( implementation )
						.build();
		}
	

	public static void main(String[] args) throws Exception {
	
		final IRentalRepositorySync repositorySync = createActiveObject(new RentalRepositorySync(), IRentalRepositorySync.class );
		
		final ILanguageHandlerOption language =  createActiveObject( new LanguageHandlerOption( repositorySync ), ILanguageHandlerOption.class );
		
		final IRentalHandlerFunctional handler =  createActiveObject( new RentalHandlerFunctional( language ), IRentalHandlerFunctional.class );
		
		////////
		////////
		////////
		////////
		
		final String idFlim = "idFlim";		
		final String idCustomer = "idCustomer";
		
		final CommandCreate commandCreate = new CommandCreate( idFlim , idCustomer );
		
		final AlterOption<RentalDto> rentalDtoOption = (AlterOption<RentalDto>) handler.create( commandCreate );
		
		final RentalDto rentalDto = rentalDtoOption.get();
		
		logger.info("\nRENTAL DTO " + rentalDto + "") ;
		
		System.exit(0);		
		

	}

}
