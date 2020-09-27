package com.microservice.moviecatalog.services.movieinfo;

import com.microservice.moviecatalog.models.Movie;
import com.microservice.moviecatalog.models.Rating;

public interface MovieInfoService {

	Movie getMoviesInformation(Rating rating);
}
