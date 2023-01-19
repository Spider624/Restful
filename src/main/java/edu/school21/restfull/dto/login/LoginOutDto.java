package edu.school21.restfull.dto.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("JWT token")
@Value
public class LoginOutDto {

	@ApiModelProperty(value = "Token", required = true)
	String token;

}
