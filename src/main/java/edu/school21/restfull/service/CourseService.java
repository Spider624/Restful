package edu.school21.restfull.service;

import edu.school21.restfull.dto.lesson.LessonCreateOutDto;
import edu.school21.restfull.dto.lesson.LessonInDto;
import edu.school21.restfull.dto.lesson.LessonOutDto;
import edu.school21.restfull.dto.lesson.LessonSortField;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.course.*;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.exception.RestfullBadRequestException;
import edu.school21.restfull.exception.RestfullNotFoundException;
import edu.school21.restfull.model.Course;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.repository.CourseRepository;
import edu.school21.restfull.repository.LessonRepository;
import edu.school21.restfull.repository.UserRepository;
import edu.school21.restfull.web.maaper.LessonMapper;
import edu.school21.restfull.web.maaper.PaginationMapper;
import edu.school21.restfull.web.maaper.CourseMapper;
import edu.school21.restfull.web.maaper.UserMapper;
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
		Course course = findAndMapC(courseId, Function.identity());
		if (courseRepository.existsByName(dto.getName())) {
			throw new RestfullBadRequestException("Course is already exists");
		}
		courseMapper.update(course, dto);

		log.debug("Course [{}] was updated", course.getName());
	}

	@Transactional
	public void deleteCourse(long courseId) {
		Course course = findAndMapC(courseId, Function.identity());
		courseRepository.delete(course);

		log.debug("Course [{}] was deleted", course.getName());
	}


	private <T> T findAndMapC(long courseId, Function<Course, T> map) {
		return courseRepository.findById(courseId)
				.map(map)
				.orElseThrow(() -> new RestfullNotFoundException("Course not found"));
	}

	@Autowired
	private LessonRepository lessonRepository;
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public LessonCreateOutDto createLesson(long courseId, LessonInDto dto) {

		Course course = courseRepository.findById(courseId).get();
		long teacherId = dto.getTeacherId();
		User teacher = userRepository.findById(teacherId).get();
		if (teacher.getRole() != UserRole.TEACHER){
			throw new RestfullBadRequestException("User is not a teacher");
		}
		if (!course.getTeachers().contains(teacher)){
			throw new RestfullBadRequestException("User is not a teacher for this course");
		}

		Lesson lesson = new Lesson();
		lessonMapper.update(lesson, dto);
		lesson.setTeacher(teacher);

		course.getLessons().add(lesson);

		lessonRepository.save(lesson);

		log.debug("Lesson [{}] was created", lesson.getId());

		return new LessonCreateOutDto(lesson.getId());
	}

	@Transactional
	public void addTeacher(long courseId, long teacherId){
		Course course = courseRepository.findById(courseId).get();
		User user = userRepository.findById(teacherId).get();
		if (user.getRole() != UserRole.TEACHER){
			throw new RestfullBadRequestException("User not a teacher");
		}
		course.getTeachers().add(user);
	}

	@Transactional
	public void removeTeacher(long courseId, long teacherId){
		Course course = courseRepository.findById(courseId).get();
		User user = userRepository.findById(teacherId).get();

		if (!course.getTeachers().remove(user)){
			throw new RestfullBadRequestException("Teacher to remove is not exists");
		}
	}

	@Transactional
	public void addStudent(long courseId, long studentId){
		Course course = courseRepository.findById(courseId).get();
		User user = userRepository.findById(studentId).get();
		if (user.getRole() != UserRole.STUDENT){
			throw new RestfullBadRequestException("User not a student");
		}
		course.getStudents().add(user);
	}

	@Transactional
	public void removeStudent(long courseId, long studentId){
		Course course = courseRepository.findById(courseId).get();
		User user = userRepository.findById(studentId).get();

		if (!course.getStudents().remove(user)){
			throw new RestfullBadRequestException("Student to remove is not exists");
		}
	}


	@Transactional(readOnly = true)
	public Page<LessonOutDto> getLessons(Pagination<LessonSortField> pagination) {
		return lessonRepository.findAll(paginationMapper.map(pagination)).map(lessonMapper::map);
	}

	@Transactional(readOnly = true)
	public LessonOutDto getLesson(long lessonId) {
		return findAndMap(lessonId, lessonMapper::map);
	}

	@Transactional
	public void updateLesson(long lessonId, LessonInDto dto) {
		Lesson lesson = findAndMap(lessonId, Function.identity());
		lessonMapper.update(lesson, dto);

		log.debug("Lesson [{}] was updated", lesson.getId());
	}

	@Transactional
	public void deleteLesson(long lessonId) {
		Lesson lesson = findAndMap(lessonId, Function.identity());
		lessonRepository.delete(lesson);

		log.debug("Lesson [{}] was deleted", lesson.getId());
	}

	private <T> T findAndMap(long lessonId, Function<Lesson, T> map) {
		return lessonRepository.findById(lessonId)
				.map(map)
				.orElseThrow(() -> new RestfullNotFoundException("Lesson not found"));
	}

//	@Transactional(readOnly = true)
//	public Page<UserOutDto> getTeachersByCourse(long courseId, Pagination<CourseSortField> pagination) {
//		return userRepository. findAll(paginationMapper.map(pagination)).map(courseMapper::map);
//	}
}
