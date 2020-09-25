package com.microservice.ratingsdataservice.models;

public class Rating {

	private String movieId;
	private int rate;

	public Rating(String movieId, int rate) {
		this.movieId = movieId;
		this.rate = rate;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
