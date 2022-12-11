package edu.school21.restfull.dto.lesson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import lombok.AllArgsConstructor;

@JsonDeserialize(using = SortFieldDeserializer.class)
@AllArgsConstructor
public enum LessonSortField implements SortField {

	ID("id"),
	START_TIME("startTime"),
	END_TIME("endTime"),
	WEEK_DAY("weekDay"),
	TEACHER("teacher");

	private final String dataFieldName;

	@Override
	public String getDataFieldName() {
		return dataFieldName;
	}

	@Override
	public String getDtoFieldName() {
		return name();
	}

}
