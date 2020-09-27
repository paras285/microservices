package com.microservice.moviecatalog.models;

import java.util.List;

public class UserRating {

	private String userId;
	private List<Rating> rating;
	
	public UserRating() {}
	
	public UserRating(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "UserRating [userId=" + userId + ", rating=" + rating + "]";
	}
	
}
