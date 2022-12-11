package edu.school21.restfull.repository;

import edu.school21.restfull.model.Course;
import edu.school21.restfull.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	Page<Lesson> findAllByCourse(Course course, Pageable pageable);

	Optional<Lesson> findByIdAndCourseId(long id, long courseId);

}
