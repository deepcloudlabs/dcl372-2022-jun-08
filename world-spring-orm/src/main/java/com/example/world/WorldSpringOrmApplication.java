package com.example.world;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@SpringBootApplication
public class WorldSpringOrmApplication implements ApplicationRunner {

	private final CountryRepository countryRepository;
	
	public WorldSpringOrmApplication(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorldSpringOrmApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		countryRepository.findOneByCode("ITA")
		                 .ifPresent( italy -> System.out.println(italy.getPopulation()));
		countryRepository.findOneByCode("FRA")
		                 .ifPresent( france -> {
		                	 france.setPopulation(france.getPopulation() + 10_000);
		                	 france.setSurfaceArea(france.getSurfaceArea() - 100_000);
		                	 countryRepository.update(france);
		                 });
		System.out.println("\nAsian countries:");
		countryRepository.getCountriesByContinent("Asia")
		                 .stream()
		                 .map(Country::getName)
		                 .forEach(System.out::println);
		System.out.println("\nContinents:");
		countryRepository.getContinents().forEach(System.out::println);
	}

}
