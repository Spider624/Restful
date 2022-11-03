package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.UserRole;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class UserBaseInDto extends UserBaseDto {

	@JsonCreator
	public UserBaseInDto(@JsonProperty("firstName") String firstName,
						 @JsonProperty("lastName") String lastName,
						 @JsonProperty("login") String login,
						 @JsonProperty("role") UserRole role) {
		super(firstName, lastName, login, role);
	}

}
