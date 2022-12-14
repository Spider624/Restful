package edu.school21.restfull;

import edu.school21.restfull.app.RestfullApplication;
import edu.school21.restfull.model.User;
import edu.school21.restfull.repository.UserRepository;
import edu.school21.restfull.security.CustomUserDetails;
import edu.school21.restfull.security.jwt.JwtAuthenticationProvider;
import edu.school21.restfull.security.jwt.JwtAuthenticationToken;
import edu.school21.restfull.security.jwt.JwtTokenManager;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestfullApplication.class)
public class AbstractTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenManager jwtTokenManager;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	protected MockMvc mockMvc;

	@Autowired
	public void setContext(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(springSecurity())
				.build();
	}

	protected void authorize(long userId) {
		// Imitate jwt authentication
		User user = userRepository.findById(userId).orElseThrow(AssertionError::new);
		String jwt = jwtTokenManager.generateToken(new CustomUserDetails(user));
		jwtAuthenticationProvider.authenticate(new JwtAuthenticationToken(jwt));
	}

}
