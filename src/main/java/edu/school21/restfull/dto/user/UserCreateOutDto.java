package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Created user info")
@Value
public class UserCreateOutDto {

	@ApiModelProperty(value = "Created user ID")
	Long id;

	@JsonCreator
	public UserCreateOutDto(@JsonProperty("id") Long id) {
		this.id = id;
	}
}
