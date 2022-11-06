package edu.school21.restfull.dto.course;

import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@NonFinal
public abstract class CourseBaseDto {

	@ApiModelProperty(value = "StartDate", required = true)
	@NotNull(message = "No start date selected")
	LocalDate startDate;

	@ApiModelProperty(value = "EndDate", required = true)
	@NotNull(message = "No end date selected")
	LocalDate endDate;

	@ApiModelProperty(value = "Name", required = true)
	@NotEmpty(message = "Name of course isn't defined")
	@Size(message = "Name Of Course incorrect", min = 1, max = 255)
	String name;

//	@ApiModelProperty(value = "Teacher", required = true)
//	@NotEmpty(message = "Teacher name isn't defined")
//	@Size(message = "Teacher name incorrect", min = 1, max = 50)
//	User teacher;
//
//	@ApiModelProperty(value = "Student", required = true)
//	@NotEmpty(message = "Student isn't defined")
//	@Size(message = "Student incorrect", min = 5, max = 50)
//	User student;

	@ApiModelProperty(value = "Description", required = true)
	@NotNull(message = "Description isn't defined")
	String description;

//	@ApiModelProperty(value = "lesson", required = true)
//	@NotNull(message = "There are no lessons in this course")
//	Lesson lesson;


}
