package edu.school21.restfull.web.handler;

import lombok.Value;

@Value
public class ErrorResponse {

	/** Error */
	Error error;

	public ErrorResponse(Integer status, String message) {
		this.error = new Error(status, message);
	}

	@Value
	public static class Error {
		/** Http status */
		Integer status;
		/** Error message */
		String message;
	}

}
