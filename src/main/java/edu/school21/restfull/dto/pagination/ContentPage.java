package edu.school21.restfull.dto.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.List;

@ApiModel("Content page and some info about page")
@Value
public class ContentPage<T> {

	@ApiModelProperty(value = "Page content", required = true)
	List<T> content;

	@ApiModelProperty(value = "Size of page", required = true)
	Integer size;

	@ApiModelProperty(value = "Number of page", required = true)
	Integer number;

	@ApiModelProperty(value = "Has next page", required = true)
	Boolean hasNext;

	@JsonCreator
	public ContentPage(@JsonProperty("content") List<T> content,
					   @JsonProperty("size") Integer size,
					   @JsonProperty("number") Integer number,
					   @JsonProperty("hasNext") Boolean hasNext) {
		this.content = content;
		this.size = size;
		this.number = number;
		this.hasNext = hasNext;
	}

}
