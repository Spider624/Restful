package edu.school21.restfull.dto.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Detail course info")
@Value
public class CourseCreateOutDto extends CourseBaseDto {

	@ApiModelProperty(value = "Created Course Id")
	Long id;

}
