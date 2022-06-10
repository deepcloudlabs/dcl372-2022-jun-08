package com.example.world.repository;

import java.util.List;
import java.util.Optional;

import com.example.world.entity.Country;

public interface CountryRepository {
	Optional<Country> findOneByCode(String code);
	List<Country> findAll(int pageNo,int pageSize);
	void insert(Country country);
	void update(Country country);
	Country deleteOneByCode(String code);	
	List<String> getContinents();
	List<Country> getCountriesByContinent(String continent);
}
