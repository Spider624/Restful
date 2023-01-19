package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import lombok.AllArgsConstructor;

@JsonDeserialize(using = SortFieldDeserializer.class)
@AllArgsConstructor
public enum CourseSortField implements SortField {

	ID("id"),
	START_DATE("startDate"),
	END_DATE("endDate"),
	NAME("name");

	private final String dataFieldName;

	@Override
	public String getDataFieldName() {
		return dataFieldName;
	}

	@Override
	public String getDtoFieldName() {
		return name();
	}

	@Override
	public Boolean isDefault() {
		return this == ID;
	}

}
