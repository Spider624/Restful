package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import lombok.AllArgsConstructor;

@JsonDeserialize(using = SortFieldDeserializer.class)
@AllArgsConstructor
public enum StudentSortField implements SortField {

	ID("student.id"),
	FIRST_NAME("student.firstName"),
	LAST_NAME("student.lastName");

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
