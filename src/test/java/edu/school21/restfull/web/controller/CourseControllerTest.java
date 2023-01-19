package edu.school21.restfull.web.controller;

import edu.school21.restfull.AbstractTest;
import edu.school21.restfull.model.Course;
import edu.school21.restfull.model.User;
import edu.school21.restfull.model.type.CourseStatus;
import edu.school21.restfull.model.type.UserRole;
import edu.school21.restfull.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class CourseControllerTest extends AbstractTest {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void publishCourse_Success() throws Exception {
		User admin = createUser("admin", "Admin", "Admin", UserRole.ADMIN);

		Course course = new Course();
		course.setName("Math");
		course.setDescription("About math");
		course.setStatus(CourseStatus.DRAFT);
		course.setStartDate(LocalDate.now());
		course.setEndDate(LocalDate.now().plusMonths(5));
		courseRepository.save(course);

		mockMvc.perform(post("/courses/{courseId}/publish", course.getId())
						.header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader(admin.getId())))
				.andDo(document("{class-name}/{method-name}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						responseFields(subsectionWithPath("id").description("Course ID"),
								subsectionWithPath("name").description("Course name"),
								subsectionWithPath("description").description("Course description"),
								subsectionWithPath("status").description("Course status"),
								subsectionWithPath("startDate").type(LocalDate.class).description("Start date of course"),
								subsectionWithPath("endDate").type(LocalDate.class).description("End date of course"),
								subsectionWithPath("_links").description("Links to other resources"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(course.getId().intValue())))
				.andExpect(jsonPath("$.name", is(course.getName())))
				.andExpect(jsonPath("$.description", is(course.getDescription())))
				.andExpect(jsonPath("$.status", is(course.getStatus().name())));
	}

}
