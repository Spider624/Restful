package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel("Course info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CourseInDto extends CourseBaseDto {

	@JsonCreator
	public CourseInDto(@JsonProperty("name") String name,
					   @JsonProperty("description") String description,
					   @JsonProperty("startDate") LocalDate startDate,
					   @JsonProperty("endDate") LocalDate endDate) {
		super(startDate, endDate, name, description);
	}

}
