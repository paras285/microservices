package com.microservice.moviecatalog.services.userrating;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.moviecatalog.models.Rating;
import com.microservice.moviecatalog.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingServiceImpl implements UserRatingService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${rating.data.service}")
	private String ratingDataService;

	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackUserRating")
	public Optional<List<Rating>> getUserRatings(String userId) {
		UserRating userRating =  restTemplate.getForObject("http://"+ratingDataService+"/ratingsdata/users/" + userId,
				UserRating.class);
		return Optional.ofNullable(userRating.getRating());
	}
	
	public Optional<List<Rating>> getFallbackUserRating(String userId) {
		return Optional.empty();
	}

}
