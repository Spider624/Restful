package edu.school21.restfull.model;

import edu.school21.restfull.model.type.WeekDay;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "restfull_lesson")
public class Lesson extends AbstractModel {

	@Column(nullable = false)
	private LocalTime startTime;

	@Column(nullable = false)
	private LocalTime endTime;

	@Column(nullable = false, length = WeekDay.LENGTH)
	private WeekDay weekDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", nullable = false)
	private User teacher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	private Course course;

}
