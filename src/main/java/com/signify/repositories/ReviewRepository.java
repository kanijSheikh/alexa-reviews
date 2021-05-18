package com.signify.repositories;

import com.signify.model.Review;

import java.util.Collection;
import java.util.List;

/**
 * 2019 Copyright (c) Maersk Corporation. All rights reserved.
 */
public interface ReviewRepository {
	void saveReviews(List<Review> reviews);

	Collection<Review> getReviews(Integer pageNumber, Integer limit, String date, String storeType, Integer rating);
}
