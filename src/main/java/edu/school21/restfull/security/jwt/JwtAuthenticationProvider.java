package edu.school21.restfull.security.jwt;

import edu.school21.restfull.security.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@Override
	public Authentication authenticate(Authentication authentication) {
		JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;

		String token = (String) jwtAuthentication.getCredentials();
		if (StringUtils.isEmpty(token)) {
			throw new BadCredentialsException("Token not specified");
		}

		CustomUserDetails userDetails = jwtTokenManager.parseToken(token);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid jwt token");
		}

		authentication = new JwtAuthenticationToken(userDetails, Collections.singletonList(userDetails.getRole()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication == JwtAuthenticationToken.class;
	}

}
