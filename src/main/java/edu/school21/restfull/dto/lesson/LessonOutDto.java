package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@ApiModel("Detail lesson info")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonOutDto extends LessonBaseDto {

	@ApiModelProperty(value = "Id")
	Long id;

	@JsonCreator
	public LessonOutDto(@JsonProperty("startDate") LocalDate startDate,
					   @JsonProperty("endDate") LocalDate endDate,
					   @JsonProperty("dayOfWeek") String dayOfWeek,
						@JsonProperty("id") Long id)
	{
		super(startDate, endDate, dayOfWeek);
		this.id = id;
	}
}
