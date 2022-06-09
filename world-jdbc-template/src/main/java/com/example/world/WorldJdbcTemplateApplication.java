package com.example.world;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@SpringBootApplication
public class WorldJdbcTemplateApplication implements ApplicationRunner {
	private final CountryRepository countryRepository;
	
	public WorldJdbcTemplateApplication(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorldJdbcTemplateApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		countryRepository.findOneByCode("TUR")
		                 .ifPresent(System.out::println);
		countryRepository.findAll(0, 25)
		                 .stream()
		                 .map(Country::getName)
		                 .forEach(System.out::println);
		// countryRepository.deleteOneByCode("TUR");
	}

}
