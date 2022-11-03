package edu.school21.restfull.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.exception.RestfullBadRequestException;

import java.io.IOException;
import java.util.Arrays;

public class SortFieldDeserializer extends JsonDeserializer<SortField> {

	@Override
	public SortField deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
		String text = jsonParser.getText();

		return Arrays.stream((SortField[]) handledType().getEnumConstants())
				.filter(e -> e.getDtoFieldName().equals(text))
				.findFirst()
				.orElseThrow(() -> new RestfullBadRequestException("Unknown sort field"));
	}

}
