package edu.school21.restfull.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum UserRole implements GrantedAuthority {

	ADMIN(Authorities.ADMIN),
	TEACHER(Authorities.TEACHER),
	STUDENT(Authorities.STUDENT);

	public static final int LENGTH = 15;

	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}

	public static class Authorities {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String TEACHER = "ROLE_TEACHER";
		public static final String STUDENT = "ROLE_STUDENT";
	}

}
