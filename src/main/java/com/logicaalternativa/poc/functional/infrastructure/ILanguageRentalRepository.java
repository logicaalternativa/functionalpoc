package com.logicaalternativa.poc.functional.infrastructure;

import com.logicaalternativa.futures.Monad;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.domain.IFilmEntity;
import com.logicaalternativa.poc.functional.domain.IRentalAggregate;
import com.logicaalternativa.poc.functional.domain.imo.CustomerEntity;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public interface ILanguageRentalRepository <T extends Monad<CustomerDto>, S extends Monad<FilmDto>, U extends Monad<CustomerInfoDto>> {
	
	T getCustomer( final String idCustomer );
	
	S getFilm( final String idFilm );

	U getExtraInfo( final String idCustomer );
	
	U getExtraInfoVip( final String idCustomer );
	
	IRentalAggregate createEntity(IFilmEntity filmEntityImp,
			CustomerEntity customerEntityImp);

}
