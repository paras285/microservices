package com.microservice.moviecatalogservice.models;

import java.util.List;

public class UserRating {

	private List<Rating> rating;

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}
	
}
