package edu.school21.restfull.dto.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;

@ApiModel("Info for pagination")
@Value
public class Pagination <T extends SortField> {

	@ApiModelProperty(value = "Size of page", required = true)
	@NotNull(message = "Page size isn't defined")
	Integer size;

	@ApiModelProperty(value = "Number of page", required = true)
	@NotNull(message = "Page number isn't defined")
	Integer number;

	@ApiModelProperty(value = "Field name")
	@NotNull(message = "Field name isn't defined")
	T sortField;

	@ApiModelProperty(value = "Direction: ascending/descending")
	@NotNull(message = "Direction isn't defined")
	Boolean ascending;

}
