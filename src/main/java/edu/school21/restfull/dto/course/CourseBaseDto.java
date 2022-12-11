package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.school21.restfull.serialization.LocalDateSerializer;
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

	@ApiModelProperty(value = "Start date", required = true)
	@NotNull(message = "Start date is not defined")
	@JsonSerialize(using = LocalDateSerializer.class)
	LocalDate startDate;

	@ApiModelProperty(value = "End date", required = true)
	@NotNull(message = "End date is not defined")
	@JsonSerialize(using = LocalDateSerializer.class)
	LocalDate endDate;

	@ApiModelProperty(value = "Name", required = true)
	@NotEmpty(message = "Name is not defined")
	@Size(message = "Name Of Course incorrect", min = 1, max = 255)
	String name;

	@ApiModelProperty(value = "Description", required = true)
	@NotNull(message = "Description is not defined")
	String description;

}
