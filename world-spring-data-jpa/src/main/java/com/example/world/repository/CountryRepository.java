package com.example.world.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.world.entity.Country;

public interface CountryRepository extends JpaRepository<Country, String>{
	@Query(nativeQuery = true, value = "select distinct continent from country")
	List<String> getContinents();
	List<Country> findAllByContinent(String continent);
	List<Country> findByPopulationBetween(long from,long to);
	List<Country> findByContinentAndPopulationBetween(String continent,int from,int to);
	Country findTopByOrderByPopulationDesc();
	Country findTopByOrderBySurfaceAreaDesc();
}
