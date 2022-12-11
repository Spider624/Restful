package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@ApiModel("Detail user info")
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserOutDto extends UserBaseDto {

	@ApiModelProperty(value = "Id")
	Long id;

	@JsonCreator
	public UserOutDto(@JsonProperty("id") Long id,
					  @JsonProperty("firstName") String firstName,
					  @JsonProperty("lastName") String lastName,
					  @JsonProperty("login") String login,
					  @JsonProperty("role") UserRole role) {
		super(firstName, lastName, login, role);
		this.id = id;
	}

}
