package com.example.imdb.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.imdb.domain.Genre;
import com.example.imdb.service.MovieService;

@Configuration
public class ImdbConfig {

	@Bean(name = "genres")
	public Collection<Genre> allGenres(MovieService movieService){
		return movieService.findAllGenres();
	}
}
