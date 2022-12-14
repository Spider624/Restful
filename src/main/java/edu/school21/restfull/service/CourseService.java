package edu.school21.restfull.service;

import edu.school21.restfull.dto.lesson.LessonCreateOutDto;
import edu.school21.restfull.dto.lesson.LessonInDto;
import edu.school21.restfull.dto.lesson.LessonOutDto;
import edu.school21.restfull.dto.lesson.LessonSortField;
import edu.school21.restfull.dto.pagination.ContentPage;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.course.*;
import edu.school21.restfull.dto.user.CourseUserDto;
import edu.school21.restfull.dto.user.StudentSortField;
import edu.school21.restfull.dto.user.TeacherSortField;
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

import javax.persistence.PersistenceContext;
import java.util.function.Function;

@Slf4j
@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LessonRepository lessonRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private PaginationMapper paginationMapper;
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private UserMapper userMapper;

	@Transactional
	public CourseCreateOutDto createCourse(CourseInDto dto) {
		if (courseRepository.existsByName(dto.getName())) {
			throw new RestfullBadRequestException("Course is already exists");
		}

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new RestfullBadRequestException("Start date should be before end date");
		}

		Course course = new Course();
		courseMapper.update(course, dto);

		courseRepository.save(course);

		log.debug("Course [{}] was created", course.getName());

		return new CourseCreateOutDto(course.getId());
	}

	@Transactional(readOnly = true)
	public ContentPage<CourseOutDto> getCourses(Pagination<CourseSortField> pagination) {
		Page<CourseOutDto> page = courseRepository.findAll(paginationMapper.map(pagination)).map(courseMapper::map);
		return paginationMapper.map(page);
	}

	@Transactional(readOnly = true)
	public CourseOutDto getCourse(long courseId) {
		return findAndMapCourse(courseId, courseMapper::map);
	}

	@Transactional
	public void updateCourse(long courseId, CourseInDto dto) {
		Course course = findCourse(courseId);
		if (!course.getName().equals(dto.getName()) && courseRepository.existsByName(dto.getName())) {
			throw new RestfullBadRequestException("Name is already busy");
		}

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new RestfullBadRequestException("Start date should be before end date");
		}

		courseMapper.update(course, dto);

		log.debug("Course [{}] was updated", course.getName());
	}

	@Transactional
	public void deleteCourse(long courseId) {
		Course course = findCourse(courseId);
		courseRepository.delete(course);

		log.debug("Course [{}] was deleted", course.getName());
	}

	@Transactional(readOnly = true)
	public ContentPage<LessonOutDto> getLessons(long courseId, Pagination<LessonSortField> pagination) {
		Page<LessonOutDto> page = lessonRepository.findAllByCourse(findCourse(courseId), paginationMapper.map(pagination))
				.map(lessonMapper::map);

		return paginationMapper.map(page);
	}

	@Transactional
	public LessonCreateOutDto createLesson(long courseId, LessonInDto dto) {
		Course course = findCourse(courseId);

		User teacher = findUser(dto.getTeacherId());
		if (teacher.getRole() != UserRole.TEACHER) {
			throw new RestfullBadRequestException("User is not a teacher");
		}

		if (!course.getTeachers().contains(teacher)) {
			throw new RestfullNotFoundException("Teacher not found in the course");
		}

		if (dto.getStartTime().isAfter(dto.getEndTime())) {
			throw new RestfullBadRequestException("Start time should be before end time");
		}

		Lesson lesson = new Lesson();
		lessonMapper.update(lesson, dto);
		lesson.setTeacher(teacher);
		lesson.setCourse(course);

		lessonRepository.save(lesson);

		log.debug("Lesson [{}] was create for course [{}]", lesson.getId(), courseId);

		return new LessonCreateOutDto(lesson.getId());
	}

	@Transactional
	public void updateLesson(long courseId, long lessonId, LessonInDto dto) {
		Lesson lesson = findLesson(courseId, lessonId);

		if (dto.getStartTime().isAfter(dto.getEndTime())) {
			throw new RestfullBadRequestException("Start time should be before end time");
		}

		lessonMapper.update(lesson, dto);

		if (dto.getStartTime().isAfter(dto.getEndTime())) {
			throw new RestfullBadRequestException("Start time should be before end time");
		}

		if (!lesson.getTeacher().getId().equals(dto.getTeacherId())) {
			User teacher = findUser(dto.getTeacherId());
			if (teacher.getRole() != UserRole.TEACHER) {
				throw new RestfullBadRequestException("User is not a teacher");
			}

			if (!lesson.getCourse().getTeachers().contains(teacher)) {
				throw new RestfullNotFoundException("Teacher not found in the course");
			}

			lesson.setTeacher(teacher);
		}

		log.debug("Lesson [{}] was updated", lesson.getId());
	}

	@Transactional
	public void deleteLesson(long courseId, long lessonId) {
		Lesson lesson = findLesson(courseId, lessonId);
		lessonRepository.delete(lesson);

		log.debug("Lesson [{}] was deleted", lesson.getId());
	}

	@Transactional(readOnly = true)
	public ContentPage<CourseUserDto> getTeachers(long courseId, Pagination<TeacherSortField> pagination) {
		Page<CourseUserDto> page = courseRepository.getTeachersByCourse(findCourse(courseId), paginationMapper.map(pagination))
				.map(userMapper::mapCourseUser);

		return paginationMapper.map(page);
	}

	@Transactional
	public void addTeacher(long courseId, long teacherId) {
		Course course = findCourse(courseId);

		User user = findUser(teacherId);
		if (user.getRole() != UserRole.TEACHER) {
			throw new RestfullBadRequestException("User is not a teacher");
		}

		if (course.getTeachers().contains(user)) {
			throw new RestfullBadRequestException("Teacher is already in the course");
		}

		course.getTeachers().add(user);

		log.debug("Teacher [{}] was added to course [{}]", teacherId, courseId);
	}

	@Transactional
	public void removeTeacher(long courseId, long teacherId) {
		Course course = findCourse(courseId);
		User user = findUser(teacherId);

		if (!course.getTeachers().remove(user)) {
			throw new RestfullNotFoundException("Teacher not found in the course");
		}

		log.debug("Teacher [{}] was removed from course [{}]", teacherId, courseId);
	}

	@Transactional(readOnly = true)
	public ContentPage<CourseUserDto> getStudents(long courseId, Pagination<StudentSortField> pagination) {
		Page<CourseUserDto> page = courseRepository.getStudentsByCourse(findCourse(courseId), paginationMapper.map(pagination))
				.map(userMapper::mapCourseUser);

		return paginationMapper.map(page);
	}

	@Transactional
	public void addStudent(long courseId, long studentId) {
		Course course = findCourse(courseId);
		User user = findUser(studentId);

		if (user.getRole() != UserRole.STUDENT) {
			throw new RestfullBadRequestException("User is not a student");
		}

		if (course.getStudents().contains(user)) {
			throw new RestfullBadRequestException("Student is already in the course");
		}

		course.getStudents().add(user);

		log.debug("Student [{}] was added to course [{}]", studentId, courseId);
	}

	@Transactional
	public void removeStudent(long courseId, long studentId) {
		Course course = findCourse(courseId);
		User user = findUser(studentId);

		if (!course.getStudents().remove(user)) {
			throw new RestfullBadRequestException("Student not found in the course");
		}

		log.debug("Student [{}] was removed from course [{}]", studentId, courseId);
	}


	private Course findCourse(long courseId) {
		return findAndMapCourse(courseId, Function.identity());
	}

	private <T> T findAndMapCourse(long courseId, Function<Course, T> map) {
		return courseRepository.findById(courseId)
				.map(map)
				.orElseThrow(() -> new RestfullNotFoundException("Course not found"));
	}

	private Lesson findLesson(long courseId, long lessonId) {
		return lessonRepository.findByIdAndCourseId(lessonId, courseId)
				.orElseThrow(() -> new RestfullNotFoundException("Lesson not found"));
	}

	private User findUser(long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RestfullNotFoundException("User not found"));
	}

}
