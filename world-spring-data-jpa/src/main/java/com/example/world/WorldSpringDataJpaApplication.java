package com.example.world;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@SpringBootApplication
public class WorldSpringDataJpaApplication implements ApplicationRunner{
	private final CountryRepository countryRepository;
	
	public WorldSpringDataJpaApplication(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorldSpringDataJpaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		countryRepository.findById("ITA")
                         .ifPresent( italy -> System.out.println(italy.getPopulation()));
		countryRepository.findById("FRA")
        .ifPresent( france -> {
	       	 france.setPopulation(france.getPopulation() + 10_000);
	       	 france.setSurfaceArea(france.getSurfaceArea() - 100_000);
	       	 countryRepository.save(france);
        });
		System.out.println("\nAsian countries:");
		countryRepository.findAllByContinent("Asia")
				        .stream()
				        .map(Country::getName)
				        .forEach(System.out::println);
		System.out.println("\nContinents:");
		countryRepository.getContinents().forEach(System.out::println);		
		System.out.println("findTopByOrderByPopulationDesc: %s".formatted(
				countryRepository.findTopByOrderByPopulationDesc()
				                 .getName()));
		System.out.println("findTopByOrderBySurfaceAreaDesc: %s".formatted(
				countryRepository.findTopByOrderBySurfaceAreaDesc()
				.getName()));
		countryRepository.findByContinentAndPopulationBetween("Asia", 10_000_000, 50_000_000)
				        .stream()
				        .forEach(country -> System.out.println("%24s: %d".formatted(country.getName(),country.getPopulation())));		
		System.out.println(countryRepository.getClass());
	}

}
