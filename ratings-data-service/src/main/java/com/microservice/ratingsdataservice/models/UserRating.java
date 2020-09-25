package com.microservice.ratingsdataservice.models;

import java.util.List;

public class UserRating {

	private int id;
	private List<Rating> rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}
	
}
