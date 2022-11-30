package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("User info for creation")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserCreateInDto extends UserBaseInDto {

	@ApiModelProperty(value = "Password", required = true)
	@NotNull(message = "Password isn't defined")
	@Size(message = "Password incorrect", min = 5)
	String password;

	@JsonCreator
	public UserCreateInDto(@JsonProperty("firstName") String firstName,
						   @JsonProperty("lastName") String lastName,
						   @JsonProperty("login") String login,
						   @JsonProperty("role") UserRole role,
						   @JsonProperty("password") String password) {
		super(firstName, lastName, login, role);
		this.password = password;
	}

}
