package edu.school21.restfull.model;

import edu.school21.restfull.model.type.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "restfull_lesson")
public class Lesson extends AbstractModel {

	@Column(nullable = false, unique = false)
	private LocalTime startTime;

	@Column(nullable = false, unique = false)
	private LocalTime endTime;

	@Column(nullable = false)
	private DayOfWeek dayOfWeek;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	private User teacher;

}
