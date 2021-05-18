package com.signify.service;

import com.signify.model.Review;
import com.signify.repositories.ReviewRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.util.Objects.isNull;

@Service
public class ReviewServiceImpl implements ReviewService {


	@Autowired
	private ReviewRepositoryImpl reviewRepository;

	@Value("${page.size}")
	private String pageNumber;

	@Value("${limit.size}")
	private String limitNumber;

	@Override
	public void saveReviews(List<Review> reviews) {
		reviewRepository.saveReviews(reviews);
	}

	@Override
	public Collection<Review> getReviews(Integer page, Integer limit, String date, String storeType, Integer rating) {
		if (isNull(page)) {
			page = valueOf(pageNumber);
		}
		if (isNull(limit)) {
			limit = valueOf(limitNumber);
		}
		return reviewRepository.getReviews(page, limit,date,storeType,rating);
	}


	@Override
	public long getCount() {
		return reviewRepository.getCount();
	}

}
