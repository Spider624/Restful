package edu.school21.restfull.dto.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.school21.restfull.model.type.CourseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@ApiModel("Detail course info")
@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "courses", itemRelation = "course")
public class CourseOutDto extends CourseBaseDto {

	@ApiModelProperty(value = "Course Id")
	Long id;
	@ApiModelProperty(value = "Status")
	CourseStatus status;

	@JsonCreator
	public CourseOutDto(@JsonProperty("id") Long id,
						@JsonProperty("status") CourseStatus status,
						@JsonProperty("name") String name,
						@JsonProperty("description") String description,
						@JsonProperty("startDate") LocalDate startDate,
						@JsonProperty("endDate") LocalDate endDate) {
		super(startDate, endDate, name, description);
		this.id = id;
		this.status = status;
	}

}
