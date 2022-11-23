package edu.school21.restfull.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restfull_course")
public class Course extends AbstractModel {

	public static final int NAME_COURSE_LENGTH = 127;
	public static final int DESCRIPTION_LENGTH = 500;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false, length = NAME_COURSE_LENGTH, unique = true)
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="teachers", nullable = false, updatable = false)
	private List<User> teachers = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="students", nullable = false, updatable = false)
	private List<User> students = new ArrayList<>();

	@Column(nullable = true, length = DESCRIPTION_LENGTH)
	private String description;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="course_id", nullable = true, updatable = true)
	private List<Lesson> lessons = new ArrayList<>();
}
