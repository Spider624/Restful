package edu.school21.restfull.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import lombok.AllArgsConstructor;

@JsonDeserialize(using = SortFieldDeserializer.class)
@AllArgsConstructor
public enum UserSortField implements SortField {

	ID("id"),
	LOGIN("login"),
	ROLE("role"),
	FIRST_NAME("firstName"),
	LAST_NAME("lastName");

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
