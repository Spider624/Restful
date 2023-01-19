package edu.school21.restfull.model;

import edu.school21.restfull.model.type.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "restfull_user")
public class User extends AbstractModel {

	public static final int NAME_LENGTH = 127;
	public static final int LOGIN_LENGTH = 50;

	@Column(nullable = false, length = NAME_LENGTH)
	private String lastName;

	@Column(nullable = false, length = NAME_LENGTH)
	private String firstName;

	@Column(nullable = false, length = LOGIN_LENGTH, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = UserRole.LENGTH)
	@Enumerated(EnumType.STRING)
	private UserRole role;

}
