package edu.school21.restfull.web.maaper;

import edu.school21.restfull.dto.course.CourseBaseInDto;
import edu.school21.restfull.dto.course.CourseOutDto;
import edu.school21.restfull.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	public abstract void update(@MappingTarget Course target, CourseBaseInDto source);

	public abstract CourseOutDto map(Course course);

}
