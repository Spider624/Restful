package edu.school21.restfull.serialization;

import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.pagination.SortField;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

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
				.orElse(null);

		Integer number = Optional.ofNullable(webRequest.getParameter("number"))
				.map(Integer::valueOf)
				.orElse(null);

		Boolean ascending = Optional.ofNullable(webRequest.getParameter("ascending"))
				.map(Boolean::valueOf)
				.orElse(Boolean.TRUE);

		Type type = parameter.getExecutable().getParameters()[parameter.getParameterIndex()].getParameterizedType();
		Type sortFieldType = ((ParameterizedTypeImpl) type).getActualTypeArguments()[0];

		String field = webRequest.getParameter("sortField");
		SortField sortField = field != null
				? getSortField(sortFieldType, f ->  f.getDtoFieldName().equals(field))
				: getSortField(sortFieldType, SortField::isDefault);

		return new Pagination<>(size, number, sortField, ascending);
	}

	private SortField getSortField(Type sortFieldType, Predicate<SortField> predicate) {
		return Arrays.stream(((Class<SortField>) sortFieldType).getEnumConstants())
				.filter(predicate)
				.findFirst()
				.orElseThrow(AssertionError::new);
	}

}
