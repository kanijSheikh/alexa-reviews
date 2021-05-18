package com.signify.service;


import com.signify.model.Review;
import com.signify.repositories.ReviewRepositoryImpl;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class ReviewServiceImplTest {

    private static final Integer PAGE = 1;
    private static final Integer LIMIT = 10;
    private static final String DATE = "12-12-2021";
    private static final String STORE_TYPE = "iTunes";
    private static final Integer RATING = 4;
    static final long REVIEW_COUNT = 20L;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepositoryImpl reviewRepository;

    List<Review> reviews = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(reviewService, "pageNumber", "1");
        ReflectionTestUtils.setField(reviewService, "limitNumber", "10");
        reviews.add(buildReview());
    }

    @Test
    void shouldSaveReviews() {
        doNothing().when(reviewRepository).saveReviews(reviews);

        reviewService.saveReviews(reviews);

        verify(reviewRepository).saveReviews(reviews);
    }

    @ParameterizedTest
    @MethodSource("getParams")
    void shouldGetReviews(Integer page, Integer limit, String date, String storeType, Integer rating, Integer defaultPage, Integer defaultLimit) {
        when(reviewRepository.getReviews(defaultPage, defaultLimit, date, storeType, rating)).thenReturn(reviews);

        Collection<Review> actualReviews = reviewService.getReviews(page, limit, date, storeType, rating);

        assertEquals(reviews, actualReviews);
    }

    @Test
    void shouldGetCountOfReviews() {
        when(reviewRepository.getCount()).thenReturn(REVIEW_COUNT);

        long actualCount = reviewService.getCount();

        assertEquals(REVIEW_COUNT, actualCount);
    }

    private static Stream<Arguments> getParams() {
        return Stream.of(
                Arguments.of(null, LIMIT, DATE, STORE_TYPE, RATING, PAGE, LIMIT),
                Arguments.of(PAGE, null, DATE, STORE_TYPE, RATING, PAGE, LIMIT),
                Arguments.of(PAGE, LIMIT, DATE, STORE_TYPE, RATING, PAGE, LIMIT)
        );
    }

    private Review buildReview() {
        Review review = new Review();
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