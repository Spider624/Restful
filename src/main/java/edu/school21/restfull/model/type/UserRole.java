package edu.school21.restfull.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

	ADMIN("ROLE_ADMIN"),
	TEACHER("ROLE_TEACHER"),
	STUDENT("ROLE_STUDENT");

	public static final int LENGTH = 15;

	private String role;

}
