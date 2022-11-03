package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.UserRole;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;


@ApiModel("User info for update")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserUpdateInDto extends UserBaseInDto {

	@JsonCreator
	public UserUpdateInDto(@JsonProperty("firstName") String firstName,
						   @JsonProperty("lastName") String lastName,
						   @JsonProperty("login") String login,
						   @JsonProperty("role") UserRole role) {
		super(firstName, lastName, login, role);
	}

}
