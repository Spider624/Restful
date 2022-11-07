package edu.school21.restfull.service;

import edu.school21.restfull.dto.lesson.*;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.exception.RestfullBadRequestException;
import edu.school21.restfull.exception.RestfullNotFoundException;
import edu.school21.restfull.model.Lesson;
import edu.school21.restfull.repository.LessonRepository;
import edu.school21.restfull.web.maaper.LessonMapper;
import edu.school21.restfull.web.maaper.PaginationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@Service
public class LessonService {

	@Autowired
	private LessonRepository lessonRepository;
	@Autowired
	private LessonMapper lessonMapper;
	@Autowired
	private PaginationMapper paginationMapper;

	@Transactional
	public LessonCreateOutDto createLesson(LessonInDto dto) {
//		if (lessonRepository.existsByDayOfWeek(dto.getDayOfWeek())) {
//			throw new RestfullBadRequestException("lesson is already exists");
//		}

		Lesson lesson = new Lesson();
		lessonMapper.update(lesson, dto);

		lessonRepository.save(lesson);

		log.debug("Lesson [{}] was created", lesson.getId());

		return new LessonCreateOutDto(lesson.getId());
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

}
