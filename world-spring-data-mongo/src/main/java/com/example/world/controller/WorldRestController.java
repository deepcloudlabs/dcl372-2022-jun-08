package com.example.world.controller;

import java.util.List;

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

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

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
	@GetMapping("/{code}")
	public Country getCountryByCode(@PathVariable String code) {
		return countryRepository.findById(code)
				                .orElseThrow(() -> new IllegalArgumentException("Cannot ind country"));
	}
	
	// http://localhost:6060/world/api/v1/countries?page=0&size=25
	@GetMapping(params= {"page", "size"})
	public List<Country> getCountriesByPage(@RequestParam int page,@RequestParam int size) {
		return countryRepository.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@PostMapping
	public Country addCountry(@RequestBody Country country) {
		return countryRepository.insert(country);
	}
	
	@PutMapping("/{code}")
	public Country updateCountry(@PathVariable String code, @RequestBody Country country) {
		return countryRepository.save(country);
	}
	
	@DeleteMapping("/{code}")
	public Country removeCountryByCode(@PathVariable String code) {
		var country = countryRepository.findById(code)
				                       .orElseThrow(() -> new IllegalArgumentException("Cannot find country"));
		countryRepository.delete(country);
		return country;
	}
}
