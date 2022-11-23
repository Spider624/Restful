package edu.school21.restfull.dto.lesson;

import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.DayOfWeek;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
@NonFinal
public abstract class LessonBaseDto {

	@ApiModelProperty(value = "StartTime", required = true)
	@NotNull(message = "No start time selected")
	LocalTime startTime;

	@ApiModelProperty(value = "EndTime", required = true)
	@NotNull(message = "No end time selected")
	LocalTime endTime;

	@ApiModelProperty(value = "DayOfWeek", required = true)
	@NotNull(message = "DayOfWeek of lesson isn't defined")
	DayOfWeek dayOfWeek;

	@ApiModelProperty(value = "teacherId", required = true)
	@NotNull(message = "Teacher is not defined")
	long teacherId;

}
