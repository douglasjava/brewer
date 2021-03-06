package com.algaworks.brewer.repository.helper.estilo;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class EstilosImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil<Estilo> paginacaoUtil;

	@Override
	@Transactional(readOnly = true)
	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable) {

		List<Predicate> predicates = new ArrayList<>();
		List<Estilo> estilos = new ArrayList<>();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Estilo> query = builder.createQuery(Estilo.class);
		Root<Estilo> estiloRoot = query.from(Estilo.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		adicionarFiltro(filtro, predicates, builder, estiloRoot, query);

		paginacaoUtil.ordenacao(pageable, builder, query, estiloRoot);
		
		estilos = manager.createQuery(query)
						  .setFirstResult(primeiroRegistro)
						  .setMaxResults(totalRegistrosPorPagina)
						  .getResultList();

		return new PageImpl<>(estilos, pageable, total(filtro));

	}

	private Long total(EstiloFilter filtro) {
		List<Predicate> predicates = new ArrayList<>();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Estilo> query = builder.createQuery(Estilo.class);
		Root<Estilo> estiloRoot = query.from(Estilo.class);
		
		adicionarFiltro(filtro, predicates, builder, estiloRoot, query);
		
		Integer total = manager.createQuery(query).getResultList().size();
		
		return total.longValue();
	}
	
	private void adicionarFiltro(EstiloFilter filtro, List<Predicate> predicates, CriteriaBuilder builder, Root<Estilo> estiloRoot, CriteriaQuery<Estilo> query) {
		
		if (filtro != null) {

			if (!isEmpty(filtro.getNome())) {
				predicates.add(builder.like(estiloRoot.get("nome"), "%" + filtro.getNome() + "%"));
			}

		}
		
		query.where(builder.and(predicates.toArray(new Predicate[] {})));
	}


}
