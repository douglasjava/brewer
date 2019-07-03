package com.algaworks.brewer.repository.helper.cidade;

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

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadeQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil<Cidade> paginacaoUtil;
	
	@Override
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable) {
		
		
		List<Predicate> predicates = new ArrayList<>();
		List<Cidade> cidades = new ArrayList<>();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cidade> query = builder.createQuery(Cidade.class);
		Root<Cidade> cidadeRoot = query.from(Cidade.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		adicionarFiltro(filtro, predicates, builder, cidadeRoot, query);		

		paginacaoUtil.ordenacao(pageable, builder, query, cidadeRoot);

		cidades = manager.createQuery(query)
						  .setFirstResult(primeiroRegistro)
						  .setMaxResults(totalRegistrosPorPagina)
						  .getResultList();

		return new PageImpl<>(cidades, pageable, total(filtro));
	}

	private Long total(CidadeFilter filtro) {
		List<Predicate> predicates = new ArrayList<>();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cidade> query = builder.createQuery(Cidade.class);
		Root<Cidade> cidadeRoot = query.from(Cidade.class);

		adicionarFiltro(filtro, predicates, builder, cidadeRoot, query);

		Integer total = manager.createQuery(query).getResultList().size();

		return total.longValue();
	}

	private void adicionarFiltro(CidadeFilter filtro, List<Predicate> predicates, CriteriaBuilder builder,
			Root<Cidade> cidadeRoot, CriteriaQuery<Cidade> query) {
		
		if (!isEmpty(filtro.getNome())) {
			predicates.add(builder.like(cidadeRoot.get("nome"), "%" + filtro.getNome() + "%"));
		}

		if (!isEmpty(filtro.getEstado())) {
			predicates.add(builder.equal(cidadeRoot.get("estado"), filtro.getEstado()));
		}

		query.where(builder.and(predicates.toArray(new Predicate[] {})));

		
		
	}

}
