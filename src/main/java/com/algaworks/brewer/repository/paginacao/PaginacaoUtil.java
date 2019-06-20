package com.algaworks.brewer.repository.paginacao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil<T> {

	public void ordenacao(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root) {
		Sort sort = pageable.getSort();
		if (sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			query.orderBy(order.isAscending() ? builder.asc(root.get(property)) : builder.desc(root.get(property)));
		}
	}

}
