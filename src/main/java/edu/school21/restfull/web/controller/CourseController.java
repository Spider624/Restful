package edu.school21.restfull.web.controller;

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
import edu.school21.restfull.model.type.CourseStatus;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.security.SecurityUtils;
import edu.school21.restfull.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@ApiOperation("Create course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PostMapping
	public EntityModel<CourseCreateOutDto> createCourse(@RequestBody @Valid CourseInDto dto) {
		CourseCreateOutDto outDto = courseService.createCourse(dto);
		return EntityModel.of(outDto, linkTo(methodOn(CourseController.class).getCourse(outDto.getId())).withRel("course"));
	}

	@ApiOperation("Get course info")
	@GetMapping("{courseId}")
	public EntityModel<CourseOutDto> getCourse(@PathVariable("courseId") long courseId) {
		CourseOutDto dto = courseService.getCourse(courseId);

		List<Link> adminLinks = new ArrayList<>();
		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object updateCourse = methodOn(CourseController.class).updateCourse(courseId, null);
			adminLinks.add(linkTo(updateCourse).withRel("updateCourse").andAffordance(afford(updateCourse)));

			Object deleteCourse = methodOn(CourseController.class).deleteCourse(courseId);
			adminLinks.add(linkTo(deleteCourse).withRel("deleteCourse").andAffordance(afford(deleteCourse)));

			if (dto.getStatus() == CourseStatus.DRAFT) {
				Object publishCourse = methodOn(CourseController.class).publishCourse(courseId);
				adminLinks.add(linkTo(publishCourse).withRel("publishCourse").andAffordance(afford(publishCourse)));
			}
		}

		return toEntityModel(dto).add(adminLinks);
	}

	@ApiOperation("Get all courses with pagination and sorting")
	@GetMapping
	public PagedModel<EntityModel<CourseOutDto>> getCourses(@RequestParam("number") Integer pageNumber,
															@RequestParam("size") Integer pageSize,
															@RequestParam(value = "sortField", required = false) @Nullable CourseSortField sortField,
															@RequestParam(value = "ascending", required = false) @Nullable Boolean ascending) {
		sortField = Optional.ofNullable(sortField).orElse(CourseSortField.ID);
		ascending = Optional.ofNullable(ascending).orElse(Boolean.TRUE);

		ContentPage<CourseOutDto> page = courseService.getCourses(new Pagination<>(pageSize, pageNumber, sortField, ascending));

		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CourseController.class).getCourses(pageNumber, pageSize, sortField, ascending)).withSelfRel());

		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object createCourse = methodOn(CourseController.class).createCourse(null);
			links.add(linkTo(createCourse).withRel("createCourse").andAffordance(afford(createCourse)));
		}

		return PagedModel.of(
				page.getContent().stream().map(this::toEntityModel).collect(Collectors.toList()),
				new PagedModel.PageMetadata(page.getPageSize(), page.getPageNumber(), page.getTotalElements(), page.getTotalPageNumber()),
				links);
	}

	@ApiOperation("Update course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PutMapping("{courseId}")
	public ResponseEntity<?> updateCourse(@PathVariable("courseId") long courseId, @RequestBody @Valid CourseInDto dto) {
		courseService.updateCourse(courseId, dto);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Publish course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PostMapping("{courseId}/publish")
	public EntityModel<CourseOutDto> publishCourse(@PathVariable("courseId") long courseId) {
		CourseOutDto outDto = courseService.publishCourse(courseId);
		return EntityModel.of(outDto, linkTo(methodOn(CourseController.class).getCourse(courseId)).withRel("course"));
	}

	@ApiOperation("Delete course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@DeleteMapping("{courseId}")
	public ResponseEntity<?> deleteCourse(@PathVariable("courseId") long courseId) {
		courseService.deleteCourse(courseId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Create lesson")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PostMapping("{courseId}/lessons")
	public EntityModel<LessonCreateOutDto> createLesson(@PathVariable("courseId") long courseId, @RequestBody @Valid LessonInDto dto) {
		LessonCreateOutDto outDto = courseService.createLesson(courseId, dto);
		return EntityModel.of(outDto, linkTo(methodOn(CourseController.class).getCourse(courseId)).withRel("course"));
	}

	@ApiOperation("Get all lessons with pagination and sorting")
	@GetMapping("{courseId}/lessons")
	public PagedModel<EntityModel<LessonOutDto>> getLessons(@PathVariable("courseId") long courseId,
															@RequestParam("number") Integer pageNumber,
															@RequestParam("size") Integer pageSize,
															@RequestParam(value = "sortField", required = false) @Nullable LessonSortField sortField,
															@RequestParam(value = "ascending", required = false) @Nullable Boolean ascending) {
		sortField = Optional.ofNullable(sortField).orElse(LessonSortField.ID);
		ascending = Optional.ofNullable(ascending).orElse(Boolean.TRUE);

		ContentPage<LessonOutDto> page = courseService.getLessons(courseId, new Pagination<>(pageSize, pageNumber, sortField, ascending));

		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CourseController.class).getLessons(courseId, pageNumber, pageSize, sortField, ascending)).withSelfRel());

		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object createLesson = methodOn(CourseController.class).createLesson(courseId, null);
			links.add(linkTo(createLesson).withRel("createLesson").andAffordance(afford(createLesson)));
		}

		return PagedModel.of(
				page.getContent().stream().map(l -> toEntityModel(courseId, l)).collect(Collectors.toList()),
				new PagedModel.PageMetadata(page.getPageSize(), page.getPageNumber(), page.getTotalElements(), page.getTotalPageNumber()),
				links);
	}

	@ApiOperation("Update lesson")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PutMapping("{courseId}/lessons/{lessonId}")
	public ResponseEntity<?> updateLesson(@PathVariable("courseId") long courseId,
										  @PathVariable("lessonId") long lessonId,
										  @RequestBody @Valid LessonInDto dto) {
		courseService.updateLesson(courseId, lessonId, dto);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Delete lesson")
	@Secured(value = UserRole.Authorities.ADMIN)
	@DeleteMapping("{courseId}/lessons/{lessonId}")
	public ResponseEntity<?> deleteLesson(@PathVariable("courseId") long courseId, @PathVariable("lessonId") long lessonId) {
		courseService.deleteLesson(courseId, lessonId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Get course teachers")
	@GetMapping("{courseId}/teachers")
	public PagedModel<EntityModel<CourseUserDto>> getTeachers(@PathVariable("courseId") long courseId,
															  @RequestParam("number") Integer pageNumber,
															  @RequestParam("size") Integer pageSize,
															  @RequestParam(value = "sortField", required = false) @Nullable TeacherSortField sortField,
															  @RequestParam(value = "ascending", required = false) @Nullable Boolean ascending) {
		sortField = Optional.ofNullable(sortField).orElse(TeacherSortField.ID);
		ascending = Optional.ofNullable(ascending).orElse(Boolean.TRUE);

		ContentPage<CourseUserDto> page = courseService.getTeachers(courseId, new Pagination<>(pageSize, pageNumber, sortField, ascending));

		List<EntityModel<CourseUserDto>> userModels = page.getContent().stream()
				.map(t -> {
					List<Link> teacherLinks = new ArrayList<>();
					if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
						Object removeTeacher = methodOn(CourseController.class).removeTeacher(courseId, t.getId());
						teacherLinks.add(linkTo(removeTeacher).withRel("removeTeacher").andAffordance(afford(removeTeacher)));
					}

					return EntityModel.of(t, teacherLinks);
				})
				.collect(Collectors.toList());

		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CourseController.class).getTeachers(courseId, pageNumber, pageSize, sortField, ascending)).withSelfRel());
		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object addTeacher = methodOn(CourseController.class).addTeacher(courseId, null);
			links.add(linkTo(addTeacher).withRel("addTeacher"));
		}

		return PagedModel.of(
				userModels,
				new PagedModel.PageMetadata(page.getPageSize(), page.getPageNumber(), page.getTotalElements(), page.getTotalPageNumber()),
				links);
	}

	@ApiOperation("Add teacher to course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PostMapping("{courseId}/teachers/{teacherId}")
	public ResponseEntity<?> addTeacher(@PathVariable("courseId") long courseId, @PathVariable("teacherId") Long teacherId){
		courseService.addTeacher(courseId, teacherId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Remove teacher from course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@DeleteMapping("{courseId}/teachers/{teacherId}")
	public ResponseEntity<?> removeTeacher(@PathVariable("courseId") long courseId, @PathVariable("teacherId") Long teacherId){
		courseService.removeTeacher(courseId, teacherId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Get course students")
	@GetMapping("{courseId}/students")
	public PagedModel<EntityModel<CourseUserDto>> getStudents(@PathVariable("courseId") long courseId,
															  @RequestParam("number") Integer pageNumber,
															  @RequestParam("size") Integer pageSize,
															  @RequestParam(value = "sortField", required = false) @Nullable StudentSortField sortField,
															  @RequestParam(value = "ascending", required = false) @Nullable Boolean ascending) {
		sortField = Optional.ofNullable(sortField).orElse(StudentSortField.ID);
		ascending = Optional.ofNullable(ascending).orElse(Boolean.TRUE);

		ContentPage<CourseUserDto> page = courseService.getStudents(courseId, new Pagination<>(pageSize, pageNumber, sortField, ascending));

		List<EntityModel<CourseUserDto>> userModels = page.getContent().stream()
				.map(s -> {
					List<Link> studentLinks = new ArrayList<>();
					if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
						Object removeStudent = methodOn(CourseController.class).removeStudent(courseId, s.getId());
						studentLinks.add(linkTo(removeStudent).withRel("removeStudent").andAffordance(afford(removeStudent)));
					}

					return EntityModel.of(s, studentLinks);
				})
				.collect(Collectors.toList());

		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CourseController.class).getStudents(courseId, pageNumber, pageSize, sortField, ascending)).withSelfRel());
		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object addStudent = methodOn(CourseController.class).addStudent(courseId, null);
			links.add(linkTo(addStudent).withRel("addStudent"));
		}

		return PagedModel.of(
				userModels,
				new PagedModel.PageMetadata(page.getPageSize(), page.getPageNumber(), page.getTotalElements(), page.getTotalPageNumber()),
				links);
	}

	@ApiOperation("Add student to course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@PostMapping("{courseId}/students/{studentId}")
	public ResponseEntity<?> addStudent(@PathVariable("courseId") long courseId, @PathVariable("studentId") Long studentId){
		courseService.addStudent(courseId, studentId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation("Remove student from course")
	@Secured(value = UserRole.Authorities.ADMIN)
	@DeleteMapping("{courseId}/students/{studentId}")
	public ResponseEntity<?> removeStudent(@PathVariable("courseId") long courseId, @PathVariable("studentId") long studentId){
		courseService.removeStudent(courseId, studentId);
		return ResponseEntity.noContent().build();
	}

	private EntityModel<CourseOutDto> toEntityModel(CourseOutDto dto) {
		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(CourseController.class).getCourse(dto.getId())).withSelfRel());
		links.add(linkTo(methodOn(CourseController.class).getLessons(dto.getId(), null, null, null, null)).withRel("getLessons"));
		links.add(linkTo(methodOn(CourseController.class).getStudents(dto.getId(), null, null, null, null)).withRel("getStudents"));
		links.add(linkTo(methodOn(CourseController.class).getTeachers(dto.getId(), null, null, null, null)).withRel("getTeachers"));

		return EntityModel.of(dto, links);
	}

	private EntityModel<LessonOutDto> toEntityModel(long courseId, LessonOutDto dto) {
		List<Link> links = new ArrayList<>();
		links.add(linkTo(methodOn(UserController.class).getUser(dto.getTeacher().getId())).withRel("getTeacher"));

		if (SecurityUtils.getCurrentUserRole() == UserRole.ADMIN) {
			Object updateLesson = methodOn(CourseController.class).updateLesson(courseId, dto.getId(), null);
			links.add(linkTo(updateLesson).withRel("updateLesson").andAffordance(afford(updateLesson)));

			Object deleteLesson = methodOn(CourseController.class).deleteLesson(courseId, dto.getId());
			links.add(linkTo(deleteLesson).withRel("deleteLesson").andAffordance(afford(deleteLesson)));
		}

		return EntityModel.of(dto, links);
	}

}
