package edu.school21.restfull.web.controller;

import edu.school21.restfull.AbstractTest;
import edu.school21.restfull.dto.user.UserBaseDto;
import edu.school21.restfull.dto.user.UserCreateInDto;
import edu.school21.restfull.dto.user.UserCreateOutDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.dto.user.UserUpdateInDto;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.school21.restfull.util.TestUtils.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class UserControllerTest extends AbstractTest {

	private User admin;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void init() {
		admin = createUser("administrator", "Administrator", "Administrator", UserRole.ADMIN);
	}

	@Test
	public void createUser_Success() throws Exception {
		UserCreateInDto createInDto = new UserCreateInDto("firstName", "lastName", "login", UserRole.ADMIN, "12345");

		MvcResult result = mockMvc.perform(post("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(createInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*.id", notNullValue()))
				.andReturn();

		EntityModel<UserCreateOutDto> entityModel = toEntityModel(result, UserCreateOutDto.class);
		assertNotNull(entityModel);

		UserCreateOutDto createOutDto = entityModel.getContent();
		assertNotNull(createOutDto);

		User user = userRepository.findAll().stream()
				.filter(u -> u.getId().equals(createOutDto.getId()))
				.findFirst()
				.orElseThrow(AssertionError::new);

		assertEquals(createOutDto.getId(), user.getId());
		assertUser(user, createInDto);
	}

	@Test
	public void createUser_LoginAlreadyBusy_Fail() throws Exception {
		createUser("login", "firstName", "lastName", UserRole.ADMIN);

		UserCreateInDto createInDto = new UserCreateInDto("firstName1", "lastName1", "login", UserRole.ADMIN, "12345");

		mockMvc.perform(post("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(createInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", is("Login is already busy")));
	}

	@Test
	public void createUser_InvalidDto_Fail() throws Exception {
		UserCreateInDto createInDto = new UserCreateInDto(null, null, null, null, null);

		mockMvc.perform(post("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(createInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", containsString("Login isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("First name isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Last name isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Role isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Password isn't defined")));

		createInDto = new UserCreateInDto("firstName1", "lastName1", "login", UserRole.ADMIN, "123");

		mockMvc.perform(post("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(createInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", containsString("Password too short")));
	}

	@Test
	public void updateUser_Success() throws Exception {
		// Create user
		UserCreateInDto createInDto = new UserCreateInDto("firstName", "lastName", "login", UserRole.ADMIN, "12345");
		MvcResult result = mockMvc.perform(post("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(createInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		EntityModel<UserCreateOutDto> entityModel = toEntityModel(result, UserCreateOutDto.class);
		assertNotNull(entityModel);

		UserCreateOutDto createOutDto = entityModel.getContent();
		assertNotNull(createOutDto);

		User user = userRepository.findAll().stream()
				.filter(u -> u.getId().equals(createOutDto.getId()))
				.findFirst()
				.orElseThrow(AssertionError::new);

		assertUser(user, createInDto);

		// Update user
		UserUpdateInDto updateInDto = new UserUpdateInDto("newFirstName", "newLastName", "newLogin", UserRole.TEACHER);
		mockMvc.perform(put("/users/{userId}", user.getId())
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(updateInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();

		user = userRepository.findById(user.getId()).orElseThrow(AssertionError::new);
		assertUser(user, updateInDto);
	}

	@Test
	public void updateUser_UserNotFound_Fail() throws Exception {
		UserUpdateInDto updateInDto = new UserUpdateInDto("newFirstName", "newLastName", "newLogin", UserRole.TEACHER);

		mockMvc.perform(put("/users/{userId}", -1)
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(updateInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error.status", is(NOT_FOUND.value())))
				.andExpect(jsonPath("$.error.message", is("User not found")));
	}

	@Test
	public void updateUser_LoginAlreadyBusy_Fail() throws Exception {
		User user1 = createUser("login", "firstName", "lastName", UserRole.ADMIN);
		User user2 = createUser("alreadyExistedLogin", "firstName", "lastName", UserRole.ADMIN);

		// Try update user login to user2's login
		UserUpdateInDto updateInDto = new UserUpdateInDto("firstName", "lastName", user2.getLogin(), UserRole.ADMIN);
		mockMvc.perform(put("/users/{userId}", user1.getId())
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(updateInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", is("Login is already busy")));
	}

	@Test
	public void updateUser_InvalidDto_Fail() throws Exception {
		UserUpdateInDto updateInDto = new UserUpdateInDto(null, null, null, null);

		mockMvc.perform(put("/users/{userId}", -1)
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId()))
						.content(toJsonBytes(updateInDto))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", containsString("Login isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("First name isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Last name isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Role isn't defined")));
	}

	@Test
	public void getUser_Success() throws Exception {
		User user = createUser("userLogin", "userFirstName", "userLastName", UserRole.TEACHER);
		createUser("otherUserLogin", "otherUserFirstName", "otherUserLastName", UserRole.STUDENT);

		MvcResult result = mockMvc.perform(get("/users/{userId}", user.getId())
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isOk())
				.andReturn();

		EntityModel<UserOutDto> entityModel = toEntityModel(result, UserOutDto.class);
		assertNotNull(entityModel);

		UserOutDto outDto = entityModel.getContent();
		assertNotNull(outDto);
		assertUser(user, outDto);
	}

	@Test
	public void getUser_UserNotFount_Success() throws Exception {
		mockMvc.perform(get("/users/{userId}", -1)
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error.status", is(NOT_FOUND.value())))
				.andExpect(jsonPath("$.error.message", is("User not found")));
	}

	@Test
	public void deleteUser_Success() throws Exception {
		User user = createUser("userLogin", "userFirstName", "userLastName", UserRole.TEACHER);
		User otherUser = createUser("otherUserLogin", "otherUserFirstName", "otherUserLastName", UserRole.STUDENT);

		mockMvc.perform(delete("/users/{userId}", user.getId())
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isNoContent())
				.andReturn();

		List<User> users = userRepository.findAll();
		assertFalse(users.contains(user));
		assertTrue(users.contains(otherUser));
	}

	@Test
	public void deleteUser_UserNotFound_Fail() throws Exception {
		mockMvc.perform(delete("/users/{userId}", -1)
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error.status", is(NOT_FOUND.value())))
				.andExpect(jsonPath("$.error.message", is("User not found")));
	}

	@Test
	public void getUsers_Success() throws Exception {
		userRepository.deleteAll();

		User lupin = createUser("lupin", "Anton", "Belov", UserRole.STUDENT);
		User zupin = createUser("zupin", "Gigi", "Hadid", UserRole.ADMIN);
		User aupin = createUser("aupin", "Zendaya", "Furiya", UserRole.TEACHER);

		mockMvc.perform(get("/users")
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(lupin.getId()))
						.param("number", "0")
						.param("size", "5")
						.param("ascending", "true")
						.param("sortField", "LOGIN"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.users", hasSize(3)))
				.andExpect(jsonPath("$._embedded.users[0].id", is(aupin.getId().intValue())))
				.andExpect(jsonPath("$._embedded.users[0].login", is(aupin.getLogin())))
				.andExpect(jsonPath("$._embedded.users[0].firstName", is(aupin.getFirstName())))
				.andExpect(jsonPath("$._embedded.users[0].lastName", is(aupin.getLastName())))
				.andExpect(jsonPath("$._embedded.users[0].role", is(aupin.getRole().name())))
				.andExpect(jsonPath("$._embedded.users[1].id", is(lupin.getId().intValue())))
				.andExpect(jsonPath("$._embedded.users[1].login", is(lupin.getLogin())))
				.andExpect(jsonPath("$._embedded.users[1].firstName", is(lupin.getFirstName())))
				.andExpect(jsonPath("$._embedded.users[1].lastName", is(lupin.getLastName())))
				.andExpect(jsonPath("$._embedded.users[1].role", is(lupin.getRole().name())))
				.andExpect(jsonPath("$._embedded.users[2].id", is(zupin.getId().intValue())))
				.andExpect(jsonPath("$._embedded.users[2].login", is(zupin.getLogin())))
				.andExpect(jsonPath("$._embedded.users[2].firstName", is(zupin.getFirstName())))
				.andExpect(jsonPath("$._embedded.users[2].lastName", is(zupin.getLastName())))
				.andExpect(jsonPath("$._embedded.users[2].role", is(zupin.getRole().name())));
	}

	@Test
	public void getUsers_ParamsNotDefined_Fail() throws Exception {
		mockMvc.perform(get("/users")
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())));
	}


	private void assertUser(User user, UserBaseDto dto) {
		assertEquals(dto.getLogin(), user.getLogin());
		assertEquals(dto.getFirstName(), user.getFirstName());
		assertEquals(dto.getLastName(), user.getLastName());
		assertEquals(dto.getRole(), user.getRole());

		if (dto instanceof UserCreateInDto) {
			assertTrue(passwordEncoder.matches(((UserCreateInDto) dto).getPassword(), user.getPassword()));
		} else if (dto instanceof UserOutDto) {
			assertEquals(((UserOutDto) dto).getId(), user.getId());
		}
	}

}
