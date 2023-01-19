package edu.school21.restfull.repository;

import edu.school21.restfull.model.Course;
import edu.school21.restfull.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface CourseRepository extends JpaRepository<Course, Long> {

	boolean existsByName(String name);

	@Query("SELECT teacher FROM Course c JOIN c.teachers teacher WHERE c = :course")
	Page<User> getTeachersByCourse(@Param("course") Course course, Pageable pageable);

	@Query("SELECT student FROM Course c JOIN c.students student WHERE c = :course")
	Page<User> getStudentsByCourse(@Param("course") Course course, Pageable pageable);

}
