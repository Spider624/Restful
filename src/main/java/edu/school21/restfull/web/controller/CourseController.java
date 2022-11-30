package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.lesson.LessonCreateOutDto;
import edu.school21.restfull.dto.lesson.LessonInDto;
import edu.school21.restfull.dto.lesson.LessonOutDto;
import edu.school21.restfull.dto.lesson.LessonSortField;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.course.*;
import edu.school21.restfull.dto.user.UserOutDto;
import edu.school21.restfull.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

//	@Autowired
//	private CourseService lessonService;

	@ApiOperation("Create course")
	@PostMapping
	public CourseCreateOutDto createCourse(@RequestBody @Valid CourseInDto dto) {
		return courseService.createCourse(dto);
	}

	@ApiOperation("Get all courses with pagination and sorting")
	@GetMapping
	public Page<CourseOutDto> getCourses(Pagination<CourseSortField> pagination) {
		return courseService.getCourses(pagination);
	}

	@ApiOperation("Get course info")
	@GetMapping("{courseId}")
	public CourseOutDto getCourse(@PathVariable("courseId") long courseId) {
		return courseService.getCourse(courseId);
	}

	@ApiOperation("Update course")
	@PutMapping("{courseId}")
	public void updateCourse(@PathVariable("courseId") long courseId, @RequestBody @Valid CourseInDto dto) {
		courseService.updateCourse(courseId, dto);
	}

	@ApiOperation("Delete course")
	@DeleteMapping("{courseId}")
	public void deleteCourse(@PathVariable("courseId") long courseId) {
		courseService.deleteCourse(courseId);
	}



	@ApiOperation("Create lesson")
	@PostMapping("{courseId}/lessons")
	public LessonCreateOutDto createLesson(@PathVariable("courseId") long courseId, @RequestBody @Valid LessonInDto dto) {
		return courseService.createLesson(courseId, dto);
	}

	@ApiOperation("Get all lessons with pagination and sorting")
	@GetMapping("{courseId}/lessons")
	public Page<LessonOutDto> getLessons(Pagination<LessonSortField> pagination) {
		return courseService.getLessons(pagination);
	}

	@ApiOperation("Get lesson info")
	@GetMapping("{courseId}/lessons/{lessonId}")
	public LessonOutDto getLesson(@PathVariable("lessonId") long lessonId) {
		return courseService.getLesson(lessonId);
	}

	@ApiOperation("Update lesson")
	@PutMapping("{courseId}/lessons/{lessonId}")
	public void updateLesson(@PathVariable("lessonId") long lessonId, @RequestBody @Valid LessonInDto dto) {
		courseService.updateLesson(lessonId, dto);
	}

	@ApiOperation("Delete lesson")
	@DeleteMapping("{courseId}/lessons/{lessonId}")
	public void deleteLesson(@PathVariable("lessonId") long lessonId) {
		courseService.deleteLesson(lessonId);
	}

	@ApiOperation("Add teacher to course")
	@PutMapping("{courseId}/teachers/{teacherId}")
	public void addTeacher(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId){
		courseService.addTeacher(courseId, teacherId);
	}

	@ApiOperation("Remove teacher from course")
	@DeleteMapping("{courseId}/teachers/{teacherId}")
	public void removeTeacher(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId){
		courseService.removeTeacher(courseId, teacherId);
	}

//	@ApiOperation("Get all courses with pagination and sorting")
//	@GetMapping("{courseId}/teachers/")
//	public Page<UserOutDto>  getTeachersByCourse(@PathVariable("courseId") long courseId, Pagination<CourseSortField> pagination) {
//		return courseService.getTeachersByCourse(courseId, pagination);
//	}

	@ApiOperation("Add student to course")
	@PutMapping("{courseId}/students/{studentId}")
	public void addStudent(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId){
		courseService.addStudent(courseId, studentId);
	}

	@ApiOperation("Remove student from course")
	@DeleteMapping("{courseId}/students/{studentId}")
	public void removeStudent(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId){
		courseService.removeStudent(courseId, studentId);
	}



}
