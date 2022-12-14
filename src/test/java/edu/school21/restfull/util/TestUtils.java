package edu.school21.restfull.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restfull.dto.pagination.ContentPage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

	public static  <O> O toObject(MvcResult result, Class<O> objectClazz) {
		try {
			return OBJECT_MAPPER.readValue(json(result), objectClazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> ContentPage<T> toPage(MvcResult result, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json(result), OBJECT_MAPPER.getTypeFactory().constructParametricType(ContentPage.class, clazz));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String json(MvcResult result) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString();
	}
}
