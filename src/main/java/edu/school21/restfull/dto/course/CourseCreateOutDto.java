package edu.school21.restfull.dto.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Created course info")
@Value
public class CourseCreateOutDto {

	@ApiModelProperty(value = "Created Course ID")
	Long id;

}
