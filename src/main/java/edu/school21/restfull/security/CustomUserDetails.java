package edu.school21.restfull.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.UserRole;
import lombok.Value;

import java.io.Serializable;

@Value
public class CustomUserDetails implements Serializable {
	private static final long serialVersionUID = 8236116535533984558L;

	Long id;
	String login;
	UserRole role;

	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.role = user.getRole();
	}

	@JsonCreator // For deserialization, from jwt token
	public CustomUserDetails(@JsonProperty("id") Long id,
							 @JsonProperty("login") String login,
							 @JsonProperty("role") UserRole role) {
		this.id = id;
		this.login = login;
		this.role = role;
	}
}
