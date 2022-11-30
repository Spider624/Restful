package edu.school21.restfull.service;

import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.user.UserCreateInDto;
import edu.school21.restfull.dto.user.UserCreateOutDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.dto.user.UserSortField;
import edu.school21.restfull.dto.user.UserUpdateInDto;
import edu.school21.restfull.exception.RestfullBadRequestException;
import edu.school21.restfull.exception.RestfullNotFoundException;
import edu.school21.restfull.model.User;
import edu.school21.restfull.repository.UserRepository;
import edu.school21.restfull.web.maaper.PaginationMapper;
import edu.school21.restfull.web.maaper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PaginationMapper paginationMapper;

	@Transactional
	public UserCreateOutDto createUser(UserCreateInDto dto) {
		if (userRepository.existsByLogin(dto.getLogin())) {
			throw new RestfullBadRequestException("Login is already busy");
		}

		User user = new User();
		userMapper.update(user, dto);
		user.setPassword(encoder.encode(dto.getPassword()));

		userRepository.save(user);

		log.debug("User [{}] was created", user.getLogin());

		return new UserCreateOutDto(user.getId());
	}

	@Transactional(readOnly = true)
	public Page<UserOutDto> getUsers(Pagination<UserSortField> pagination) {
		return userRepository.findAll(paginationMapper.map(pagination)).map(userMapper::map);
	}

	@Transactional(readOnly = true)
	public UserOutDto getUser(long userId) {
		return findAndMap(userId, userMapper::map);
	}

	@Transactional
	public void updateUser(long userId, UserUpdateInDto dto) {
		User user = findAndMap(userId, Function.identity());
		if (userRepository.existsByLogin(dto.getLogin())) {
			throw new RestfullBadRequestException("Login is already busy");
		}
		userMapper.update(user, dto);

		log.debug("User [{}] was updated", user.getLogin());
	}

	@Transactional
	public void deleteUser(long userId) {
		User user = findAndMap(userId, Function.identity());
		userRepository.delete(user);

		log.debug("User [{}] was deleted", user.getLogin());
	}


	private <T> T findAndMap(long userId, Function<User, T> map) {
		return userRepository.findById(userId)
				.map(map)
				.orElseThrow(() -> new RestfullNotFoundException("User not found"));
	}

}
