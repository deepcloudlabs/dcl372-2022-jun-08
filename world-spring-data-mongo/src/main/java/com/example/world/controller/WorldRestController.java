package com.example.world.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.validation.ISO3;
import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

// REST API -> Integration
// i. resource-oriented: resource -> Country      -> countries
//    rest over http ->  create -> http post      -> request body
//                       update -> http put/patch -> request body
//                       delete	-> http delete    -> response body
//                       query  -> http get       -> response body
//                   ? resource -> URL: http(s)://localhost:6060/world/api/v1/countries
//              representation : application/json, application/xml, plain/text, ...

// ii. rpc-style: command
//     GraphQL, gRpc, ...

// OpenAPI Documentation: http://localhost:6060/world/api/v1/swagger-ui/index.html
@RestController
@RequestScope
@RequestMapping("countries")
@CrossOrigin("*")
@Validated
public class WorldRestController {
	private final CountryRepository countryRepository;
	
	public WorldRestController(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	// http://localhost:6060/world/api/v1/countries/USA
	@Operation(summary = "Get country by code", description = "Get country by country code")
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/{code}")
	public Country getCountryByCode(@PathVariable @ISO3 String code) {
		return countryRepository.findById(code)
				                .orElseThrow(() -> new IllegalArgumentException("Cannot find country"));
	}
	
	// http://localhost:6060/world/api/v1/countries?page=0&size=25
	@Operation(summary = "Get countries by page", description = "Get countries by pagination")
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping(params= {"page", "size"})
	public List<Country> getCountriesByPage(
			@RequestParam @PositiveOrZero
			int page,
			@RequestParam @Min(10) @Max(50)
			int size) {
		return countryRepository.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@Operation(summary = "Create country", description = "Create country")
	@SecurityRequirement(name = "Bearer Authentication")
	@PostMapping
	public Country addCountry(@RequestBody @Validated Country country) {
		return countryRepository.insert(country);
	}
	
	@Operation(summary = "Update country by code", description = "Update country")
	@SecurityRequirement(name = "Bearer Authentication")
	@PutMapping("/{code}")
	public Country updateCountry(@PathVariable @ISO3 String code, @RequestBody @Validated Country country) {
		return countryRepository.save(country);
	}
	
	@Operation(summary = "Delete country by code", description = "Destroy country")
	@SecurityRequirement(name = "Bearer Authentication")
	@DeleteMapping("/{code}")
	public Country removeCountryByCode(@PathVariable @ISO3 String code) {
		var country = countryRepository.findById(code)
				                       .orElseThrow(() -> new IllegalArgumentException("Cannot find country"));
		countryRepository.delete(country);
		return country;
	}
}
