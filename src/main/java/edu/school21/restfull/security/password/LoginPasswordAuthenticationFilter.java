package edu.school21.restfull.security.password;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public LoginPasswordAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, AuthenticationEntryPoint entryPoint) {
		super(defaultFilterProcessesUrl, authenticationManager);
		setAuthenticationFailureHandler(entryPoint::commence);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		return this.getAuthenticationManager().authenticate(new LoginPasswordAuthenticationToken(login, password));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException {
		chain.doFilter(request, response);
	}

}
