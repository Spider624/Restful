package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.school21.restfull.model.type.WeekDay;
import edu.school21.restfull.serialization.LocalTimeDeserializer;
import edu.school21.restfull.serialization.LocalTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Value
@NonFinal
public abstract class LessonBaseDto {

	@ApiModelProperty(value = "Start time", required = true)
	@NotNull(message = "Start time is not defined")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	LocalTime startTime;

	@ApiModelProperty(value = "End time", required = true)
	@NotNull(message = "End time is not defined")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	LocalTime endTime;

	@ApiModelProperty(value = "Day of week", required = true)
	@NotNull(message = "Day of week is not defined")
	WeekDay weekDay;

}
