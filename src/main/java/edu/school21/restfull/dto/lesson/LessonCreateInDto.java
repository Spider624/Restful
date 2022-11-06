package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel("Lesson info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonCreateInDto extends LessonBaseInDto {

	@ApiModelProperty(value = "DayOfWeek", required = true)
	@NotEmpty(message = "DayOfWeek isn't defined")
	@Size(message = "DayOfWeek incorrect", min = 4)
	String dayOfWeek;

	@JsonCreator
	public LessonCreateInDto(@JsonProperty("startDate") LocalDate startDate,
							 @JsonProperty("endDate") LocalDate endDate,
							 @JsonProperty("dayOfWeek") String dayOfWeek
//			,
//						   @JsonProperty("teacher") User teacher
	)
	{
		super(startDate, endDate, dayOfWeek
				//, teacher
		);
		this.dayOfWeek = dayOfWeek;
	}

}
