package com.microservice.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.ratingsdataservice.models.Rating;
import com.microservice.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping(value="/ratingsdata")
public class RatingResource {
	
	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId,4);
	}

	@GetMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("5678", 3));
		UserRating userRating = new UserRating();
		userRating.setRating(ratings);
		return userRating;
	}

}
