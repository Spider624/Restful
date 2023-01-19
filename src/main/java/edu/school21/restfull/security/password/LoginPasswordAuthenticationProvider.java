package edu.school21.restfull.security.password;

import edu.school21.restfull.model.User;
import edu.school21.restfull.repository.UserRepository;
import edu.school21.restfull.security.CustomUserDetails;
import edu.school21.restfull.support.TransactionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class LoginPasswordAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TransactionHelper transactionHelper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginPasswordAuthenticationToken loginPasswordAuthentication = (LoginPasswordAuthenticationToken) authentication;

		String login = (String) loginPasswordAuthentication.getPrincipal();
		String password = (String) loginPasswordAuthentication.getCredentials();
		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
			throw new BadCredentialsException("User login/password is not specified");
		}

		User user = transactionHelper.executeReadOnly(() -> userRepository.findByLogin(login));
		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Login or password incorrect");
		}

		authentication = new LoginPasswordAuthenticationToken(new CustomUserDetails(user), Collections.singletonList(user.getRole()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication == LoginPasswordAuthenticationToken.class;
	}

}
