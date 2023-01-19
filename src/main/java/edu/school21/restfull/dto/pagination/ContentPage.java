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

	@ApiModelProperty(value = "Total elements", required = true)
	Long totalElements;

	@ApiModelProperty(value = "Size of page", required = true)
	Integer pageSize;

	@ApiModelProperty(value = "Number of page", required = true)
	Integer pageNumber;

	@ApiModelProperty(value = "Total pages number", required = true)
	Integer totalPageNumber;

	@JsonCreator
	public ContentPage(@JsonProperty("content") List<T> content,
					   @JsonProperty("totalElements") Long totalElements,
					   @JsonProperty("pageSize") Integer pageSize,
					   @JsonProperty("pageNumber") Integer pageNumber,
					   @JsonProperty("totalPageNumber") Integer totalPageNumber) {
		this.content = content;
		this.totalElements = totalElements;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalPageNumber = totalPageNumber;
	}

}
