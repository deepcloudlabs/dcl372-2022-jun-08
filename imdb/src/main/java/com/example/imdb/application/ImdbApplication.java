package com.example.imdb.application;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.imdb.service.MovieService;

// Ctrl + Shift + O (organize imports)
// Ctrl + Shift + F (format source code)
public class ImdbApplication {
	public static void main(String[] args) {
		System.out.println("ImdbApplication is running...");
		ConfigurableApplicationContext container = new AnnotationConfigApplicationContext("com.example.imdb.service");
		System.out.println("container is ready.");
		container.getBean(MovieService.class)
		         .findAllMoviesByYearRange(1970, 1980)
		         .forEach(System.out::println);
		container.close();
	}
}
