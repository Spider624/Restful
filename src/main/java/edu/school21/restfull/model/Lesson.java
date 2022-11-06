package edu.school21.restfull.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "restfull_lesson")
public class Lesson extends AbstractModel {

	public static final int NAME_COURSE_LENGTH = 177;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private String dayOfWeek;

//	@Column(nullable = false, unique = true)
//	private User teacher;

}
