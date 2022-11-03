package edu.school21.restfull.dto.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Info for pagination")
@Value
public class Pagination <T extends SortField> {

	@ApiModelProperty(value = "Size of page", required = true)
	Integer size;
	@ApiModelProperty(value = "Number of page", required = true)
	Integer number;
	@ApiModelProperty(value = "Field name", required = true)
	T field;
	@ApiModelProperty(value = "Direction: ascending/descending", required = true)
	Boolean ascending;

}
