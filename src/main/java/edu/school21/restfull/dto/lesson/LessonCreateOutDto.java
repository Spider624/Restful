package edu.school21.restfull.dto.lesson;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel("Created lesson info")
@Value
public class LessonCreateOutDto {

	@ApiModelProperty(value = "Created Lesson ID")
	Long id;

}
