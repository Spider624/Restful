package edu.school21.restfull.web.maaper;

import edu.school21.restfull.dto.lesson.LessonInDto;
import edu.school21.restfull.dto.lesson.LessonOutDto;
import edu.school21.restfull.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class LessonMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	public abstract void update(@MappingTarget Lesson target, LessonInDto source);

	public abstract LessonOutDto map(Lesson lesson);

}
