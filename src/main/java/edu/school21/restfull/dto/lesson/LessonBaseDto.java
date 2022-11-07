package edu.school21.restfull.dto.lesson;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@NonFinal
public abstract class LessonBaseDto {

	@ApiModelProperty(value = "StartDate", required = true)
	@NotNull(message = "No start date selected")
	LocalDate startDate;

	@ApiModelProperty(value = "EndDate", required = true)
	@NotNull(message = "No end date selected")
	LocalDate endDate;

	@ApiModelProperty(value = "DayOfWeek", required = true)
	@NotEmpty(message = "DayOfWeek of lesson isn't defined")
	@Size(message = "DayOfWeek Of Lesson incorrect", min = 1, max = 10)
	String dayOfWeek;

}
