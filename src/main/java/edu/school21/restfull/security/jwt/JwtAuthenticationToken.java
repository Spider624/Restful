package edu.school21.restfull.security.jwt;

import edu.school21.restfull.security.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final String token;
	private final CustomUserDetails userDetails;

	public JwtAuthenticationToken(String token) {
		super(null);
		this.token = token;
		this.userDetails = null;

		setAuthenticated(false);
	}

	public JwtAuthenticationToken(CustomUserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.userDetails = userDetails;
		this.token = null;

		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

}
