package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@ApiModel("Lesson info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonInDto extends LessonBaseDto {

	@JsonCreator
	public LessonInDto(@JsonProperty("startDate") LocalDate startDate,
					   @JsonProperty("endDate") LocalDate endDate,
					   @JsonProperty("dayOfWeek") String dayOfWeek)
	{
		super(startDate, endDate, dayOfWeek);
	}

}
