package edu.school21.restfull.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Created user info")
@Value
public class UserCreateOutDto {

	@ApiModelProperty(value = "Created user ID")
	Long id;

}
