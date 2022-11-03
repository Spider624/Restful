package edu.school21.restfull.dto.user;

import edu.school21.restfull.model.type.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@NonFinal
public abstract class UserBaseDto {

	@ApiModelProperty(value = "First name", required = true)
	@NotEmpty(message = "First name isn't defined")
	@Size(message = "First name incorrect", min = 1, max = 50)
	String firstName;

	@ApiModelProperty(value = "Last name", required = true)
	@NotEmpty(message = "Last name isn't defined")
	@Size(message = "Last name incorrect", min = 1, max = 50)
	String lastName;

	@ApiModelProperty(value = "Login", required = true)
	@NotEmpty(message = "Login isn't defined")
	@Size(message = "Login incorrect", min = 5, max = 50)
	String login;

	@ApiModelProperty(value = "Role", required = true)
	@NotNull(message = "Role isn't defined")
	UserRole role;

}
