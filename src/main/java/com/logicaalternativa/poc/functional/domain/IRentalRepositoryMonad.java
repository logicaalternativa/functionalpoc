package com.logicaalternativa.poc.functional.domain;

import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;

public interface IRentalRepositoryMonad <T extends Monad<IRentalAggregate>>{
	
	T create( RentalDto dto );

	T save(IRentalAggregate aggregate);


}
