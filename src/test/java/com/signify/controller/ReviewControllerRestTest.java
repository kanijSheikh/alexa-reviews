package com.signify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signify.exception.RestExceptionHandler;
import com.signify.model.Review;
import com.signify.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
class ReviewControllerRestTest {


    @InjectMocks
    private ReviewControllerRest reviewControllerRest;

    @Mock
    private ReviewService reviewService;

    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reviewControllerRest)
                .setControllerAdvice(new RestExceptionHandler()).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void saveReviews() throws Exception {
        List<Review> reviews = new ArrayList<>();
        reviews.add(buildReview());
        doNothing().when(reviewService).saveReviews(reviews);

        MvcResult response = mvc.perform(post("/api/reviews", 10)
                .content(objectMapper.writeValueAsString(reviews))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    void getReviews() throws Exception {
        List<Review> reviews = new ArrayList<>();
        reviews.add(buildReview());

        when(reviewService.getReviews(any(), any(), any(), any(), any())).thenReturn(reviews);
        when(reviewService.getCount()).thenReturn(1L);

        MvcResult response  = mvc.perform(get("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("page", "1")
                .param("limit", "10")
                .param("date", "12-12-2021")
                .param("storeType", "iTunes")
                .param("rating", "4"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, response.getResponse().getStatus());
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