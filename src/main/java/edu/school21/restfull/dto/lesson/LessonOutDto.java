package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.DayOfWeek;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel("Detail lesson info")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonOutDto extends LessonBaseDto {

	@ApiModelProperty(value = "Id")
	Long id;

	@JsonCreator
	public LessonOutDto(@JsonProperty("startTime") LocalTime startTime,
						@JsonProperty("endTime") LocalTime endTime,
						@JsonProperty("dayOfWeek") DayOfWeek dayOfWeek,
						@JsonProperty("id") Long id,
						@JsonProperty("teacherId") long teacherId)
	{
		super(startTime, endTime, dayOfWeek, teacherId);
		this.id = id;
	}
}
