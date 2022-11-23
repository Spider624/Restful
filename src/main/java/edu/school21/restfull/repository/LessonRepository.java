package edu.school21.restfull.repository;

import edu.school21.restfull.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	boolean existsByDayOfWeek(String dayOfWeek);

//		@Query("select t.id from User t")
//		User searchCustom();

}
