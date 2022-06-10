package com.example.world.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.world.entity.Country;

public interface CountryRepository extends MongoRepository<Country, String>{
//	@Query("{}")
//	List<String> getContinents();
	List<Country> findAllByContinent(String continent);
	List<Country> findByPopulationBetween(long from,long to);
	List<Country> findByContinentAndPopulationBetween(String continent,int from,int to);
	Country findTopByOrderByPopulationDesc();
	Country findTopByOrderBySurfaceAreaDesc();
}
