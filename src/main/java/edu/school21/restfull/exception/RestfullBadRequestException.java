package edu.school21.restfull.exception;

import org.springframework.http.HttpStatus;

public class RestfullBadRequestException extends RestfullRuntimeException {

	public RestfullBadRequestException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

}
