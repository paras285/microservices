package com.microservice.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.moviecatalogservice.models.CatalogItem;
import com.microservice.moviecatalogservice.models.Movie;
import com.microservice.moviecatalogservice.models.UserRating;
import com.netflix.discovery.DiscoveryClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * Discovery client is not being used but it can be used to have information about services
	 */
	@SuppressWarnings("unused")
	@Autowired
	private DiscoveryClient client;

	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// Get all rated movies
		// For each movie, call movie-service and get its details
		// Put them all together

		// List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new
		// Rating("5678", 3));
		// RestTemplate restTemplate = new RestTemplate();

		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId,
				UserRating.class);
		return userRating.getRating().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Description", rating.getRate());
		}).collect(Collectors.toList());

		// return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));

	}

}
