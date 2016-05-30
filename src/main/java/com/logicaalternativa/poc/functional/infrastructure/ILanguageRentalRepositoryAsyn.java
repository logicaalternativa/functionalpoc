package com.logicaalternativa.poc.functional.infrastructure;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.poc.functional.app.dto.CustomerInfoDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.CustomerDto;
import com.logicaalternativa.poc.functional.infrastructure.dto.FilmDto;

public interface ILanguageRentalRepositoryAsyn extends
		ILanguageRentalRepository<AlternativeFuture<CustomerDto>,AlternativeFuture<FilmDto>, AlternativeFuture<CustomerInfoDto>> {

	AlternativeFuture<CustomerDto> getCustomer( final String idCustomer );
	
	AlternativeFuture<FilmDto> getFilm( final String idFilm );

	AlternativeFuture<CustomerInfoDto> getExtraInfo( final String idCustomer );
	
	AlternativeFuture<CustomerInfoDto> getExtraInfoVip( final String idCustomer );
	
}
