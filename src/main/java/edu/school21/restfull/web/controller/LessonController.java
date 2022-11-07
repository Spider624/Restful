package edu.school21.restfull.web.controller;

import edu.school21.restfull.dto.lesson.LessonCreateOutDto;
import edu.school21.restfull.dto.lesson.LessonInDto;
import edu.school21.restfull.dto.lesson.LessonOutDto;
import edu.school21.restfull.dto.lesson.LessonSortField;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.service.LessonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonService lessonService;

	@ApiOperation("Create lesson")
	@PostMapping
	public LessonCreateOutDto createLesson(@RequestBody @Valid LessonInDto dto) {
		return lessonService.createLesson(dto);
	}

	@ApiOperation("Get all lessons with pagination and sorting")
	@GetMapping
	public Page<LessonOutDto> getLessons(Pagination<LessonSortField> pagination) {
		return lessonService.getLessons(pagination);
	}

	@ApiOperation("Get lesson info")
	@GetMapping("{lessonId}")
	public LessonOutDto getLesson(@PathVariable("lessonId") long lessonId) {
		return lessonService.getLesson(lessonId);
	}

	@ApiOperation("Update lesson")
	@PutMapping("{lessonId}")
	public void updateLesson(@PathVariable("lessonId") long lessonId, @RequestBody LessonInDto dto) {
		lessonService.updateLesson(lessonId, dto);
	}

	@ApiOperation("Delete lesson")
	@DeleteMapping("{lessonId}")
	public void deleteLesson(@PathVariable("lessonId") long lessonId) {
		lessonService.deleteLesson(lessonId);
	}

}
