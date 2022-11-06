package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;


@ApiModel("User info for update")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonUpdateInDto extends LessonBaseInDto {

	@JsonCreator
	public LessonUpdateInDto(@JsonProperty("StartDate") LocalDate startDate,
							 @JsonProperty("EndDate") LocalDate endDate,
							 @JsonProperty("DayOfWeek") String dayOfWeek
//			,
//						   @JsonProperty("Teacher") User teacher
	)
	{
		super(startDate, endDate, dayOfWeek
				//, teacher
		);
	}

}
