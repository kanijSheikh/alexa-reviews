package com.signify.controller;


import com.signify.model.Response;
import com.signify.model.Review;
import com.signify.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/api/reviews")
public class ReviewControllerRest {

    @Autowired
    private ReviewService reviewService;

    @ApiOperation(value = "Get Reviews", response = ResponseEntity.class)
    @GetMapping(produces = "application/json")
    public ResponseEntity<Response> getReviews(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "limit", required = false) Integer limit,
                                               @RequestParam(name = "date", required = false) String date,
                                               @RequestParam(name = "storeType", required = false) String storeType,
                                               @RequestParam(name = "rating", required = false) Integer rating) {

        Collection<Review> notifications = reviewService.getReviews(page, limit, date, storeType, rating);
        long count = reviewService.getCount();
        return ResponseEntity.ok(new Response(OK, count, notifications));
    }

    @ApiOperation(value = "Add Customer Reviews", response = ResponseEntity.class)
    @PostMapping(produces = "application/json")
    public ResponseEntity<Response> saveReviews(@RequestBody List<Review> reviews) {
        reviewService.saveReviews(reviews);
        return ResponseEntity.ok(new Response(CREATED, "Reviews saved successfully"));
    }
}
