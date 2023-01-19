package edu.school21.restfull.web.handler;

import edu.school21.restfull.exception.RestfullRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RestfullRuntimeException.class)
	public ResponseEntity<ErrorResponse> restfullRuntimeExceptionHandler(RestfullRuntimeException e) {
		e.printStackTrace();
		return new ResponseEntity<>(new ErrorResponse(e.getStatus().value(), e.getMessage()), e.getStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		e.printStackTrace();

		String message = e.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining("; "));

		return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(AccessDeniedException e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> httpMessageNotReadableExceptionHandler(MissingServletRequestParameterException e) {
		e.printStackTrace();
		return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	private ErrorResponse buildResponse(HttpStatus status, String message) {
		return new ErrorResponse(status.value(), message);
	}

}
