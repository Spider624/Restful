package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel("Course info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CourseCreateInDto extends CourseBaseInDto {

	@ApiModelProperty(value = "Name", required = true)
	@NotEmpty(message = "Name isn't defined")
	@Size(message = "Name incorrect", min = 5)
	String name;

	@JsonCreator
	public CourseCreateInDto(@JsonProperty("startDate") LocalDate startDate,
							 @JsonProperty("endDate") LocalDate endDate,
							 @JsonProperty("name") String name,
							 //@JsonProperty("teacher") User teacher,
							 //@JsonProperty("student") User student,
							 @JsonProperty("description") String description )//,
							//@JsonProperty("Lesson") Lesson lesson)
	{
		super(startDate, endDate, name,
				//teacher, student,
				description
				//, lesson
		);
		this.name = name;
	}

}
