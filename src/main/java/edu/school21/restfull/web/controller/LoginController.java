package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.login.LoginOutDto;
import edu.school21.restfull.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@ApiOperation("Login user into app")
	@PostMapping("/signIn")
	public LoginOutDto login(String login, String password) {
		return loginService.login();
	}

}
