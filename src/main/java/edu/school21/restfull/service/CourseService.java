package edu.school21.restfull.service;

import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.course.*;
import edu.school21.restfull.exception.RestfullBadRequestException;
import edu.school21.restfull.exception.RestfullNotFoundException;
import edu.school21.restfull.model.Course;
import edu.school21.restfull.repository.CourseRepository;
import edu.school21.restfull.web.maaper.PaginationMapper;
import edu.school21.restfull.web.maaper.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private PaginationMapper paginationMapper;

	@Transactional
	public CourseCreateOutDto createCourse(CourseInDto dto) {
		if (courseRepository.existsByName(dto.getName())) {
			throw new RestfullBadRequestException("Course is already exists");
		}

		Course course = new Course();
		courseMapper.update(course, dto);

		courseRepository.save(course);

		log.debug("Course [{}] was created", course.getName());

		return new CourseCreateOutDto(course.getId());
	}

	@Transactional(readOnly = true)
	public Page<CourseOutDto> getCourses(Pagination<CourseSortField> pagination) {
		return courseRepository.findAll(paginationMapper.map(pagination)).map(courseMapper::map);
	}

	@Transactional(readOnly = true)
	public CourseOutDto getCourse(long courseId) {
//		return findAndMap(courseId, courseMapper::map);
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RestfullNotFoundException("Course not found"));
		return courseMapper.map(course);
	}

	@Transactional
	public void updateCourse(long courseId, CourseInDto dto) {
		Course course = findAndMap(courseId, Function.identity());
		courseMapper.update(course, dto);

		log.debug("Course [{}] was updated", course.getName());
	}

	@Transactional
	public void deleteCourse(long courseId) {
		Course course = findAndMap(courseId, Function.identity());
		courseRepository.delete(course);

		log.debug("Course [{}] was deleted", course.getName());
	}


	private <T> T findAndMap(long courseId, Function<Course, T> map) {
		return courseRepository.findById(courseId)
				.map(map)
				.orElseThrow(() -> new RestfullNotFoundException("Course not found"));
	}

}
