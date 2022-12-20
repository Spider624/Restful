package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.pagination.ContentPage;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.user.UserCreateInDto;
import edu.school21.restfull.dto.user.UserCreateOutDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.dto.user.UserSortField;
import edu.school21.restfull.dto.user.UserUpdateInDto;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.security.SecurityUtils;
import edu.school21.restfull.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.Link;
import org.springframework.security.access.annotation.Secured;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Secured(value = UserRole.Authorities.ADMIN)
	@ApiOperation("Create user")
	@PostMapping
	public EntityModel<UserCreateOutDto> createUser(@RequestBody @Valid UserCreateInDto dto) {
		UserCreateOutDto outDto = userService.createUser(dto);
		return EntityModel.of(outDto, linkTo(methodOn(UserController.class).getUser(outDto.getId())).withRel("user"));
	}

	@ApiOperation("Get all users with pagination and sorting")
	@GetMapping
	public PagedModel<EntityModel<UserOutDto>> getUsers(@RequestParam("number") Integer pageNumber,
														@RequestParam("size") Integer pageSize,
														@RequestParam(value = "sortField", required = false) @Nullable UserSortField sortField,
														@RequestParam(value = "ascending", required = false) @Nullable Boolean ascending) {
		sortField = Optional.ofNullable(sortField).orElse(UserSortField.ID);
		ascending = Optional.ofNullable(ascending).orElse(Boolean.TRUE);

		ContentPage<UserOutDto> page = userService.getUsers(new Pagination<>(pageSize, pageNumber, sortField, ascending));

		Link selfLink = linkTo(methodOn(UserController.class).getUsers(pageNumber, pageSize, sortField, ascending)).withSelfRel();
		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			selfLink = selfLink.andAffordance(afford(methodOn(UserController.class).createUser(null)));
		}

		return PagedModel.of(
				page.getContent().stream().map(this::toEntityModel).collect(Collectors.toList()),
				new PagedModel.PageMetadata(page.getPageSize(), page.getPageNumber(), page.getTotalElements(), page.getTotalPageNumber()),
				selfLink);
	}

	@ApiOperation("Get user info")
	@GetMapping("{userId}")
	public EntityModel<UserOutDto> getUser(@PathVariable("userId") long userId) {
		UserOutDto dto = userService.getUser(userId);

		List<Affordance> affordances = new ArrayList<>();
		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			affordances.add(afford(methodOn(UserController.class).updateUser(dto.getId(), null)));
			affordances.add(afford(methodOn(UserController.class).deleteUser(dto.getId())));
		}

		return toEntityModel(dto, affordances)
				.add(linkTo(methodOn(UserController.class).getUsers(null, null, null, null)).withRel("users"));
	}

	@Secured(value = UserRole.Authorities.ADMIN)
	@ApiOperation("Update user")
	@PutMapping("{userId}")
	public ResponseEntity<?> updateUser(@PathVariable("userId") long userId, @RequestBody @Valid UserUpdateInDto dto) {
		userService.updateUser(userId, dto);
		return ResponseEntity.noContent().build();
	}

	@Secured(UserRole.Authorities.ADMIN)
	@ApiOperation("Delete user")
	@DeleteMapping("{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	private EntityModel<UserOutDto> toEntityModel(UserOutDto dto) {
		return toEntityModel(dto, Collections.emptyList());
	}

	private EntityModel<UserOutDto> toEntityModel(UserOutDto dto, List<Affordance> affordances) {
		return EntityModel.of(dto,
				linkTo(methodOn(UserController.class).getUser(dto.getId())).withSelfRel().andAffordances(affordances));
	}

}
