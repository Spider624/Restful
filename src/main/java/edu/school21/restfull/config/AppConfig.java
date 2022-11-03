package edu.school21.restfull.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.serialization.PaginationHandlerMethodArgumentResolver;
import edu.school21.restfull.serialization.SortFieldDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

	@Bean
	public ObjectMapper objectMapper() {
		return new Jackson2ObjectMapperBuilder()
				.deserializerByType(SortField.class, new SortFieldDeserializer())
				.build();
	}

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PaginationHandlerMethodArgumentResolver());
	}

}
