package com.signify.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collection;

/**
 * 2018  Copyright (c) Maersk Corporation. All rights reserved.
 */
@Data
public class Response {

	private String message;

	private HttpStatus httpStatus;

	private long count;

	private Collection<Review> reviews;

	public Response(String message) {
		this.message = message;
	}

	public Response(HttpStatus httpStatus, String message) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public Response(HttpStatus httpStatus, long count, Collection<Review> reviews) {
		this.httpStatus = httpStatus;
		this.count = count;
		this.reviews = reviews;
	}
}
