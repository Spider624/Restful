package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.User;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDate;

@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class LessonBaseInDto extends LessonBaseDto {

	@JsonCreator
	public LessonBaseInDto(@JsonProperty("StartDate") LocalDate startDate,
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
