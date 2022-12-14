package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.pagination.ContentPage;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.user.UserCreateInDto;
import edu.school21.restfull.dto.user.UserCreateOutDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.dto.user.UserSortField;
import edu.school21.restfull.dto.user.UserUpdateInDto;
import edu.school21.restfull.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation("Create user")
	@PostMapping
	public UserCreateOutDto createUser(@RequestBody @Valid UserCreateInDto dto) {
		return userService.createUser(dto);
	}

	@ApiOperation("Get all users with pagination and sorting")
	@GetMapping
	public ContentPage<UserOutDto> getUsers(@Valid Pagination<UserSortField> pagination) {
		return userService.getUsers(pagination);
	}

	@ApiOperation("Get user info")
	@GetMapping("{userId}")
	public UserOutDto getUser(@PathVariable("userId") long userId) {
		return userService.getUser(userId);
	}

	@ApiOperation("Update user")
	@PutMapping("{userId}")
	public void updateUser(@PathVariable("userId") long userId, @RequestBody @Valid UserUpdateInDto dto) {
		userService.updateUser(userId, dto);
	}

	@ApiOperation("Delete user")
	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable("userId") long userId) {
		userService.deleteUser(userId);
	}

}
