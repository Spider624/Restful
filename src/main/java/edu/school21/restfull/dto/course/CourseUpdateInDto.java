package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@ApiModel("User info for update")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CourseUpdateInDto extends CourseBaseInDto {

	@JsonCreator
	public CourseUpdateInDto(@JsonProperty("startDate") LocalDate startDate,
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
