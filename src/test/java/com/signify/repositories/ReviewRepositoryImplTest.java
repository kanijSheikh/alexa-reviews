package com.signify.repositories;


import com.fasterxml.uuid.Generators;
import com.signify.model.Review;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryImplTest {

    private static final Integer PAGE = 1;
    private static final Integer LIMIT = 10;
    private static final String DATE = "12-12-2021";
    private static final String STORE_TYPE = "iTunes";
    private static final Integer RATINGS = 4;
    private static final Long REVIEW_COUNT = 20L;
    private static final String COLL_NAME_REVIEW = "Review";
    private static final String REVIEWED_DATE = "reviewedDate";
    private static final String RATING = "rating";
    private static final String REVIEW_SOURCE = "reviewSource";

    @InjectMocks
    private ReviewRepositoryImpl reviewRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    List<Review> reviews = new ArrayList<>();

    @BeforeEach
    void setUp() {
        reviews.add(buildReview());
    }
    @Test
    void shouldSaveReviews() {

        ArgumentCaptor<Review> captor = forClass(Review.class);

        reviewRepository.saveReviews(reviews);

        verify(mongoTemplate).save(captor.capture());
        Review actualReview = captor.getValue();
        assertEquals("WarcryxD", actualReview.getAuthor());
        assertNotNull(actualReview.getId());
        assertEquals("Amazon Alexa", actualReview.getProductName());
        assertEquals(4, actualReview.getRating());
        assertEquals("Excellent", actualReview.getTitle());
        assertEquals("Good Quality", actualReview.getReview());
        assertEquals("2018-01-12T02:27:03.000Z", actualReview.getReviewedDate());
        assertEquals("iTunes", actualReview.getReviewSource());

    }

    @ParameterizedTest
    @MethodSource("getParams")
    void shouldGetReviews(String date, String storeType, Integer rating,Query criteria) {

        when(mongoTemplate.find(criteria, Review.class, COLL_NAME_REVIEW)).thenReturn(reviews);

        Collection<Review> actualReviews = reviewRepository.getReviews(PAGE, LIMIT, date, storeType, rating);

        assertEquals(this.reviews, actualReviews);
    }

    @Test
    void shouldSGetCount() {
        Query query = new Query();
       when(mongoTemplate.count(query, COLL_NAME_REVIEW)).thenReturn(REVIEW_COUNT);

        long actualCount = reviewRepository.getCount();

        assertEquals(REVIEW_COUNT, actualCount);
    }

    private static Stream<Arguments> getParams() {
        Query query = getQueryPagination();
        query.addCriteria(Criteria.where("rating").is(RATINGS));

        Query query1 = getQueryPagination();
        query1.addCriteria(Criteria.where(REVIEWED_DATE).regex(DATE).and(REVIEW_SOURCE).is(STORE_TYPE).and(RATING).is(RATINGS));

        Query query2 = getQueryPagination();
        query2.addCriteria(Criteria.where(REVIEW_SOURCE).is(STORE_TYPE));

        Query query3 = getQueryPagination();
        query3.addCriteria(Criteria.where(REVIEWED_DATE).regex(DATE));

        Query query4 = getQueryPagination();
        query4.addCriteria(Criteria.where(REVIEWED_DATE).regex(DATE).and(REVIEW_SOURCE).is(STORE_TYPE));

        Query query5 = getQueryPagination();
        query5.addCriteria(Criteria.where(REVIEWED_DATE).regex(DATE).and(RATING).is(RATINGS));

        Query query6 = getQueryPagination();
        query6.addCriteria(Criteria.where(REVIEW_SOURCE).is(STORE_TYPE).and(RATING).is(RATINGS));

        return Stream.of(
                Arguments.of(null, null, RATINGS, query),
                Arguments.of(DATE, STORE_TYPE, RATINGS, query1),
                Arguments.of(null, STORE_TYPE, null, query2),
                Arguments.of(DATE, null, null, query3),
                Arguments.of(DATE, STORE_TYPE, null, query4),
                Arguments.of(DATE, null, RATINGS, query5),
                Arguments.of(null, STORE_TYPE, RATINGS, query6)
        );
    }

    private static Query getQueryPagination() {
        Query query = new Query();
        query.skip((PAGE - 1L) * LIMIT);
        query.limit(LIMIT);
        return query;
    }

    private Review buildReview() {
        Review review = new Review();
        review.setId(Generators.timeBasedGenerator().generate().toString());
        review.setAuthor("WarcryxD");
        review.setProductName("Amazon Alexa");
        review.setRating(4);
        review.setTitle("Excellent");
        review.setReview("Good Quality");
        review.setReviewedDate("2018-01-12T02:27:03.000Z");
        review.setReviewSource("iTunes");
        return review;
    }

}