package com.logicaalternativa.poc.functional.domain;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.dto.RentalDto;

public interface IRentalRepositoryAsync  extends IRentalRepositoryMonad<AlternativeFuture<IRentalAggregate>> {
	
	AlternativeFuture<IRentalAggregate> create( RentalDto dto );

	AlternativeFuture<IRentalAggregate> save(IRentalAggregate aggregate);
}
