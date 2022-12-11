package edu.school21.restfull.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Course user info (teacher/student)")
@Value
public class CourseUserDto {

	@ApiModelProperty(value = "Id", required = true)
	Long id;

	@ApiModelProperty(value = "First name", required = true)
	String firstName;

	@ApiModelProperty(value = "Last name", required = true)
	String lastName;

}
