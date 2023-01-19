package edu.school21.restfull.model;

import edu.school21.restfull.model.type.CourseStatus;
import lombok.AccessLevel;
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

	@Column(nullable = false, length = DESCRIPTION_LENGTH)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = CourseStatus.LENGTH)
	private CourseStatus status = CourseStatus.DRAFT;

	@Setter(AccessLevel.NONE)
	@ManyToMany
	@JoinTable(name = "teachers_courses",
			joinColumns = @JoinColumn(name = "course_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "teacher_id", nullable = false, updatable = false))
	private List<User> teachers = new ArrayList<>();

	@Setter(AccessLevel.NONE)
	@ManyToMany
	@JoinTable(name = "students_courses",
			joinColumns = @JoinColumn(name = "course_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "student_id", nullable = false, updatable = false))
	private List<User> students = new ArrayList<>();

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "course", orphanRemoval = true)
	private List<Lesson> lessons = new ArrayList<>();

}
