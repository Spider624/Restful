package edu.school21.restfull.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
		if (p.getText().length() == 0) {
			return null;
		}

		return LocalTime.parse(p.getText(), FORMATTER);
	}

}
