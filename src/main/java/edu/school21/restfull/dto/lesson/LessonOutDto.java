package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.dto.user.CourseUserDto;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.model.type.WeekDay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalTime;

@ApiModel("Detail lesson info")
@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonOutDto extends LessonBaseDto {

	@ApiModelProperty(value = "Id", required = true)
	Long id;

	@ApiModelProperty(value = "teacher", required = true)
	CourseUserDto teacher;

	@JsonCreator
	public LessonOutDto(@JsonProperty("startTime") LocalTime startTime,
						@JsonProperty("endTime") LocalTime endTime,
						@JsonProperty("weekDay") WeekDay weekDay,
						@JsonProperty("id") Long id,
						@JsonProperty("teacher") CourseUserDto teacher) {
		super(startTime, endTime, weekDay);
		this.id = id;
		this.teacher = teacher;
	}

}
