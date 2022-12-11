package edu.school21.restfull.web.maaper;

import edu.school21.restfull.dto.user.CourseUserDto;
import edu.school21.restfull.dto.user.UserBaseInDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "password", ignore = true)
	public abstract void update(@MappingTarget User target, UserBaseInDto source);

	public abstract UserOutDto map(User user);

	public abstract CourseUserDto mapCourseUser(User user);

}
