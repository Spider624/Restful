package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@ApiModel("Detail course info")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CourseOutDto extends CourseBaseDto {

	@ApiModelProperty(value = "Created course Id")
	Long id;

	@JsonCreator
	public CourseOutDto(@JsonProperty("id") Long id,
						@JsonProperty("name") String name,
						@JsonProperty("description") String description,
						@JsonProperty("startDate") LocalDate startDate,
						@JsonProperty("endDate") LocalDate endDate) {
		super(startDate, endDate, name, description);
		this.id = id;
	}

}
