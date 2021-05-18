package com.signify.repositories;

import com.fasterxml.uuid.Generators;
import com.signify.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 2019 Copyright (c) Maersk Corporation. All rights reserved.
 */
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final String COLL_NAME_REVIEW = "Review";
    private static final String REVIEWED_DATE = "reviewedDate";
    private static final String RATING = "rating";
    private static final String REVIEW_SOURCE = "reviewSource";

    @Autowired
    private MongoTemplate mongoTemplate;


    public void saveReviews(List<Review> reviews) {
        for (Review review : reviews) {
            final String UUID = Generators.timeBasedGenerator().generate().toString();
            review.setId(UUID);
            mongoTemplate.save(review);
        }
    }


    public Collection<Review> getReviews(Integer pageNumber, Integer limit, String date, String storeType, Integer rating) {
        Query query = new Query();
        query.skip((pageNumber - 1L) * limit);
        query.limit(limit);

        if (isNull(date) && isNull(storeType) && nonNull(rating)) {
            query.addCriteria(Criteria.where(RATING).is(rating));

        } else if (isNull(date) && nonNull(storeType) && isNull(rating)) {
            query.addCriteria(Criteria.where(REVIEW_SOURCE).is(storeType));

        } else if (nonNull(date) && isNull(storeType) && isNull(rating)) {
            query.addCriteria(Criteria.where(REVIEWED_DATE).regex(date));

        } else if (nonNull(date) && nonNull(storeType) && nonNull(rating)) {
            query.addCriteria(Criteria.where(REVIEWED_DATE).regex(date).and(REVIEW_SOURCE).is(storeType).and(RATING).is(rating));

        } else if (nonNull(date) && nonNull(storeType)) {
            query.addCriteria(Criteria.where(REVIEWED_DATE).regex(date).and(REVIEW_SOURCE).is(storeType));

        } else if (nonNull(date) && nonNull(rating)) {
            query.addCriteria(Criteria.where(REVIEWED_DATE).regex(date).and(RATING).is(rating));

        } else if (nonNull(storeType) && nonNull(rating)) {
            query.addCriteria(Criteria.where(REVIEW_SOURCE).is(storeType).and(RATING).is(rating));

        }
        return mongoTemplate.find(query, Review.class, COLL_NAME_REVIEW);
    }


    public long getCount() {
        Query query = new Query();
        return mongoTemplate.count(query, COLL_NAME_REVIEW);
    }

}

