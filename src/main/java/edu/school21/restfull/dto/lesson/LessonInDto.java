package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.WeekDay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@ApiModel("Lesson info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonInDto extends LessonBaseDto {

	@ApiModelProperty(value = "Teacher id", required = true)
	@NotNull(message = "Teacher is not defined")
	Long teacherId;

	@JsonCreator
	public LessonInDto(@JsonProperty("startTime") LocalTime startTime,
					   @JsonProperty("endTime") LocalTime endTime,
					   @JsonProperty("weekDay") WeekDay weekDay,
					   @JsonProperty("teacherId") Long teacherId) {
		super(startTime, endTime, weekDay);
		this.teacherId = teacherId;
	}

}
