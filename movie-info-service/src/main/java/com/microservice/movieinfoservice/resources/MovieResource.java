package com.microservice.movieinfoservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.movieinfoservice.models.Movie;

@RestController
@RequestMapping(value="/movies")
public class MovieResource {

	@GetMapping(value = "/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		return new Movie(movieId, "TestName");
	}
}
