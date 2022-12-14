package edu.school21.restfull.web.maaper;

import edu.school21.restfull.dto.pagination.ContentPage;
import edu.school21.restfull.dto.pagination.Pagination;
import edu.school21.restfull.dto.pagination.SortField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationMapper {

	public <T extends Enum<T> & SortField> Pageable map(Pagination<T> pagination) {
		return PageRequest.of(pagination.getNumber(),
				pagination.getSize(),
				Sort.by(map(pagination.getSortField(), pagination.getAscending())));
	}

	public <T extends SortField> Sort.Order map(T sortField, boolean ascending) {
		return ascending
				? Sort.Order.asc(sortField.getDataFieldName())
				: Sort.Order.desc(sortField.getDataFieldName());
	}

	public <T> ContentPage<T> map(Page<T> page) {
		return new ContentPage<>(page.getContent(), page.getNumber(), page.getSize(), page.hasNext());
	}

}
