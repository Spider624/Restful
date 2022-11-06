package edu.school21.restfull.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;

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

	@Column(nullable = false, length = NAME_COURSE_LENGTH)
	private String name;

//	@Column(nullable = false, unique = true)
//	private ArrayList<User> teacher;
//
//	@Column(nullable = false, unique = true)
//	private ArrayList<User> student;

	@Column(nullable = false, length = DESCRIPTION_LENGTH)
	private String description;

//	@Column(nullable = false)
//	private ArrayList<Lesson> lesson;



}
