package com.microservice.moviecatalog.services.movieinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.moviecatalog.models.Movie;
import com.microservice.moviecatalog.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfoServiceImpl implements MovieInfoService{

	@Value("${movie.info.service}")
	private String movieInfoService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String FALLBACKMOVIEID 		= "-1";
	private static final String FALLBACKMOVIENAME 		= "No Movie to display";
	private static final String FALLBACKMOVIEDESCRIPTION = "No description available";
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackforMoviesInformation")
	public Movie getMoviesInformation(Rating rating) {
		return restTemplate.getForObject("http://"+movieInfoService+"/movies/{movieId}", Movie.class,
				rating.getMovieId());
	}
	
	public Movie getFallbackforMoviesInformation(Rating rating) {
		return new Movie(FALLBACKMOVIEID,FALLBACKMOVIENAME,FALLBACKMOVIEDESCRIPTION);
	}

}
