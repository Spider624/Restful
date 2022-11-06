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

	@ApiModelProperty(value = "Description", required = true)
	@NotNull(message = "Description isn't defined")
	String description;

}
