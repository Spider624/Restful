package edu.school21.restfull;

import edu.school21.restfull.app.RestfullApplication;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.repository.UserRepository;
import edu.school21.restfull.security.CustomUserDetails;
import edu.school21.restfull.security.jwt.JwtAuthenticationProvider;
import edu.school21.restfull.security.jwt.JwtAuthenticationToken;
import edu.school21.restfull.security.jwt.JwtTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Profile("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = RestfullApplication.class)
public class AbstractTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenManager jwtTokenManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setContext(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.alwaysDo(print())
				.apply(springSecurity())
				.apply(documentationConfiguration(restDocumentation))
				.build();
	}

	protected String jwtAuthorizationHeader(long userId) {
		User user = userRepository.findById(userId).orElseThrow(AssertionError::new);
		String jwt = jwtTokenManager.generateToken(new CustomUserDetails(user));
		return "Bearer " + jwt;
	}

	protected User createUser(String login, String firstName, String lastName, UserRole role) {
		User user = new User();
		user.setLogin(login);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRole(role);
		user.setPassword(passwordEncoder.encode("12345"));

		return userRepository.save(user);
	}

}
