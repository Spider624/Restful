package edu.school21.restfull.service;

import edu.school21.restfull.dto.login.LoginOutDto;
import edu.school21.restfull.security.jwt.JwtTokenManager;
import edu.school21.restfull.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private JwtTokenManager jwtTokenManager;

	public LoginOutDto login() {
		return new LoginOutDto(jwtTokenManager.generateToken(SecurityUtils.getCurrentUserDetails()));
	}

}
