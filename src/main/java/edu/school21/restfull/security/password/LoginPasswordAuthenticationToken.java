package edu.school21.restfull.security.password;

import edu.school21.restfull.security.CustomUserDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginPasswordAuthenticationToken extends AbstractAuthenticationToken {

	private final String login;
	private final String password;
	private final CustomUserDetails userDetails;

	public LoginPasswordAuthenticationToken(String login, String password) {
		super(null);
		this.login = login;
		this.password = password;
		this.userDetails = null;

		setAuthenticated(false);
	}

	public LoginPasswordAuthenticationToken(CustomUserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.userDetails = userDetails;
		this.login = null;
		this.password = null;

		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return password;
	}

	@Override
	public Object getPrincipal() {
		return ObjectUtils.firstNonNull(login, userDetails);
	}

}
