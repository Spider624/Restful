package edu.school21.restfull.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String TOKEN_PREFIX = "Bearer";

	public JwtAuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager, AuthenticationEntryPoint entryPoint) {
		super(requestMatcher, authenticationManager);
		setAuthenticationFailureHandler(entryPoint::commence);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			token = token.split(" ")[1];
		} else {
			token = null;
		}

		return this.getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

}
