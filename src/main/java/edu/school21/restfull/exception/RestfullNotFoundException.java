package edu.school21.restfull.exception;

import org.springframework.http.HttpStatus;

public class RestfullNotFoundException extends RestfullRuntimeException {

	public RestfullNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

}
