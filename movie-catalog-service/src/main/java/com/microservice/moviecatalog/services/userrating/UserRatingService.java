package com.microservice.moviecatalog.services.userrating;

import java.util.List;
import java.util.Optional;

import com.microservice.moviecatalog.models.Rating;

public interface UserRatingService {

	Optional<List<Rating>> getUserRatings(String userId);

}
