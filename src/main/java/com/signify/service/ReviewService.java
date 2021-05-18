package com.signify.service;

import com.signify.model.Review;

import java.util.Collection;
import java.util.List;

public interface ReviewService {

	void saveReviews(List<Review> reviews);

	Collection<Review> getReviews(Integer page, Integer limit, String date, String storeType, Integer rating);

	long getCount();
}
