package edu.school21.restfull.dto.lesson;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;
import lombok.experimental.NonFinal;

@ApiModel("Detail lesson info")
@Value
@NonFinal
public class LessonCreateOutDto {

	@ApiModelProperty(value = "Created Lesson ID")
	Long id;

}
