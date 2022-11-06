package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDate;

@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class CourseBaseInDto extends CourseBaseDto {

	@JsonCreator
	public CourseBaseInDto(@JsonProperty("startDate") LocalDate startDate,
						   @JsonProperty("endDate") LocalDate endDate,
						   @JsonProperty("name") String name,
						   //@JsonProperty("teacher") User teacher,
						   //@JsonProperty("student") User student,
						   @JsonProperty("description") String description )//,
						   //@JsonProperty("lesson") Lesson lesson)
	{
		super(startDate, endDate, name,
				//teacher, student,
				description
				//, lesson
				);
	}

}
