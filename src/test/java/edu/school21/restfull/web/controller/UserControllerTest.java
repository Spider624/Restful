package edu.school21.restfull.web.controller;

import edu.school21.restfull.AbstractTest;
import edu.school21.restfull.dto.pagination.ContentPage;
import edu.school21.restfull.dto.user.UserBaseDto;
import edu.school21.restfull.dto.user.UserCreateInDto;
import edu.school21.restfull.dto.user.UserCreateOutDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.dto.user.UserSortField;
import edu.school21.restfull.dto.user.UserUpdateInDto;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.school21.restfull.util.TestUtils.toJsonBytes;
import static edu.school21.restfull.util.TestUtils.toObject;
import static edu.school21.restfull.util.TestUtils.toPage;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
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
				.andReturn();

		UserCreateOutDto createOutDto = toObject(result, UserCreateOutDto.class);
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

		UserCreateOutDto createOutDto = toObject(result, UserCreateOutDto.class);
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
				.andExpect(status().isOk())
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

		UserOutDto outDto = toObject(result, UserOutDto.class);
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
				.andExpect(status().isOk())
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

		User firstUser = createUser("lupin", "Anton", "Belov", UserRole.STUDENT);
		User secondUser = createUser("zupin", "Gigi", "Hadid", UserRole.ADMIN);
		User thirdUser = createUser("aupin", "Zendaya", "Furiya", UserRole.TEACHER);

		Map<UserSortField, List<User>> ascOrderBySort = new HashMap<>();
		ascOrderBySort.put(UserSortField.ID, Arrays.asList(firstUser, secondUser, thirdUser));
		ascOrderBySort.put(UserSortField.LOGIN, Arrays.asList(thirdUser, firstUser, secondUser));
		ascOrderBySort.put(UserSortField.FIRST_NAME, Arrays.asList(firstUser, secondUser, thirdUser));
		ascOrderBySort.put(UserSortField.LAST_NAME, Arrays.asList(firstUser, thirdUser, secondUser));
		ascOrderBySort.put(UserSortField.ROLE, Arrays.asList(secondUser, firstUser, thirdUser));

		List<UserSortField> sortFields = new ArrayList<>(Arrays.asList(UserSortField.values()));
		sortFields.add(null);
		for (UserSortField sortField : sortFields) {
			for (Boolean ascending : Arrays.asList(Boolean.TRUE, Boolean.FALSE, null)) {
				MvcResult result = mockMvc.perform(get("/users")
								.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(firstUser.getId()))
								.param("number", "0")
								.param("size", "5")
								.param("ascending", ascending != null ? ascending.toString() : null)
								.param("sortField", sortField != null ? sortField.name() : null))
						.andExpect(status().isOk())
						.andReturn();

				ascending = ascending == null ? Boolean.TRUE : ascending; // true is default value for ascending
				sortField = sortField == null ? UserSortField.ID : sortField; // id is default value for sortField

				ContentPage<UserOutDto> page = toPage(result, UserOutDto.class);
				assertNotNull(page);

				List<UserOutDto> actual = page.getContent();

				List<User> expected = new ArrayList<>(ascOrderBySort.get(sortField));
				if (!ascending) {
					Collections.reverse(expected);
				}

				assertEquals(expected.size(), actual.size());
				for (int i = 0; i < expected.size(); ++i) {
					assertUser(expected.get(i), actual.get(i));
				}
			}
		}
	}

	@Test
	public void getUsers_ParamsNotDefined_Fail() throws Exception {
		mockMvc.perform(get("/users")
				.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.status", is(BAD_REQUEST.value())))
				.andExpect(jsonPath("$.error.message", containsString("Page size isn't defined")))
				.andExpect(jsonPath("$.error.message", containsString("Page number isn't defined")));
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

	private User createUser(String login, String firstName, String lastName, UserRole role) {
		User user = new User();
		user.setLogin(login);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRole(role);
		user.setPassword(passwordEncoder.encode("12345"));

		return userRepository.save(user);
	}
}
