package edu.school21.restfull.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restfull.dto.pagination.ContentPage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static byte [] toJsonBytes(Object o) {
		try {
			return OBJECT_MAPPER.writeValueAsBytes(o);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <O> EntityModel<O> toEntityModel(MvcResult result, Class<O> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json(result), OBJECT_MAPPER.getTypeFactory().constructParametricType(EntityModel.class, clazz));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String json(MvcResult result) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString();
	}
}
