package edu.school21.restfull.serialization;

import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.pagination.SortField;
import edu.school21.restfull.exception.RestfullBadRequestException;
import edu.school21.restfull.exception.RestfullRuntimeException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class PaginationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Pagination.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) {

		Integer size = Optional.ofNullable(webRequest.getParameter("size"))
				.map(Integer::valueOf)
				.orElseThrow(() -> new RestfullBadRequestException("Page size is not specified"));

		Integer number = Optional.ofNullable(webRequest.getParameter("number"))
				.map(Integer::valueOf)
				.orElseThrow(() -> new RestfullBadRequestException("Page number is not specified"));

		Boolean ascending = Optional.ofNullable(webRequest.getParameter("ascending"))
				.map(Boolean::valueOf)
				.orElseThrow(() -> new RestfullBadRequestException("Sort direction is not specified"));

		Type type = parameter.getExecutable().getParameters()[parameter.getParameterIndex()].getParameterizedType();
		Type sortFieldType = ((ParameterizedTypeImpl) type).getActualTypeArguments()[0];

		String field = Optional.ofNullable(webRequest.getParameter("field"))
				.orElseThrow(() -> new RestfullBadRequestException("Sort field is not specified"));

		SortField sortField = Arrays.stream(((Class<SortField>) sortFieldType).getEnumConstants())
				.filter(e -> e.getDtoFieldName().equals(field))
				.findFirst()
				.orElseThrow(() -> new RestfullBadRequestException("Unknown sort field"));

		return new Pagination<>(size, number, sortField, ascending);
	}

}
