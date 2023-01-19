package edu.school21.restfull.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restfull.web.handler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
		int status = HttpStatus.FORBIDDEN.value();
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().write(MAPPER.writeValueAsBytes(new ErrorResponse(status, e.getMessage())));
	}

}
