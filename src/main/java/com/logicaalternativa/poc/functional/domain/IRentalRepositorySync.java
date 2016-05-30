package com.logicaalternativa.poc.functional.domain;

import com.logicaalternativa.poc.functional.app.dto.RentalDto;

public interface IRentalRepositorySync {
	
	IRentalAggregate create( RentalDto dto );

	IRentalAggregate save(IRentalAggregate aggregate);

}
