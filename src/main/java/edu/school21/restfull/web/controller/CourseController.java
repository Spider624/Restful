package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.course.*;
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

}
