package edu.school21.restfull.exception;

import org.springframework.http.HttpStatus;

public class RestfullRuntimeException extends RuntimeException {

	private HttpStatus status;

	public RestfullRuntimeException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public RestfullRuntimeException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public RestfullRuntimeException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
