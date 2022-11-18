package edu.school21.restfull.security;

import edu.school21.restfull.model.type.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

	public static long getCurrentUserId() {
		return getCurrentUserDetails().getId();
	}

	public static UserRole getCurrentUserRole() {
		return getCurrentUserDetails().getRole();
	}

	public static CustomUserDetails getCurrentUserDetails() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
