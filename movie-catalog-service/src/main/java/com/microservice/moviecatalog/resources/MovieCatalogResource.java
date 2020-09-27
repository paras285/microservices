package com.microservice.moviecatalog.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.moviecatalog.models.CatalogItem;
import com.microservice.moviecatalog.models.Movie;
import com.microservice.moviecatalog.models.Rating;
import com.microservice.moviecatalog.services.movieinfo.MovieInfoService;
import com.microservice.moviecatalog.services.userrating.UserRatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	/*
	 * Discovery client is not being used but it can be used to have information
	 * about services
	 */
	@SuppressWarnings("unused")
	@Autowired
	private DiscoveryClient client;

	@Autowired
	private UserRatingService userRatingService;
	
	@Autowired
	private MovieInfoService movieInfoService;
	
	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		Optional<List<Rating>> userRating = userRatingService.getUserRatings(userId);
		
		List<Rating> ratings = userRating.orElse(new ArrayList<>());
		
		return ratings.stream().map(rating -> {
			Movie movie = movieInfoService.getMoviesInformation(rating);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRate());
		}).collect(Collectors.toList());
	}
	
}
