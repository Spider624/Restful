package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import lombok.AllArgsConstructor;

@JsonDeserialize(using = SortFieldDeserializer.class)
@AllArgsConstructor
public enum TeacherSortField implements SortField {

	ID("teacher.id"),
	FIRST_NAME("teacher.firstName"),
	LAST_NAME("teacher.lastName");

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
