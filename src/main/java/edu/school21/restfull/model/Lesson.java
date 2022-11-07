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

	public static final int NAME_DAY_OF_WEEK_LENGTH = 10;

	@Column(nullable = false, unique = false)
	private LocalDate startDate;

	@Column(nullable = false, unique = false)
	private LocalDate endDate;

	@Column(nullable = false, length = NAME_DAY_OF_WEEK_LENGTH, unique = false)
	private String dayOfWeek;

//	@Column(nullable = false, unique = false)
//	private User teacher;

}
