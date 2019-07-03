package com.algaworks.brewer.repository.helper.cliente;

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

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil<Cliente> paginacaoUtil;

	@Override
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable) {

		List<Predicate> predicates = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
		Root<Cliente> clienteRoot = query.from(Cliente.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		adicionarFiltro(filtro, predicates, builder, clienteRoot, query);		

		paginacaoUtil.ordenacao(pageable, builder, query, clienteRoot);

		clientes = manager.createQuery(query)
						  .setFirstResult(primeiroRegistro)
						  .setMaxResults(totalRegistrosPorPagina)
						  .getResultList();

		return new PageImpl<>(clientes, pageable, total(filtro));
	}

	private Long total(ClienteFilter filtro) {

		List<Predicate> predicates = new ArrayList<>();

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
		Root<Cliente> clienteRoot = query.from(Cliente.class);

		adicionarFiltro(filtro, predicates, builder, clienteRoot, query);

		Integer total = manager.createQuery(query).getResultList().size();

		return total.longValue();
	}

	private void adicionarFiltro(ClienteFilter filtro, List<Predicate> predicates, CriteriaBuilder builder,
			Root<Cliente> clienteRoot, CriteriaQuery<Cliente> query) {

		if (!isEmpty(filtro.getNome())) {
			predicates.add(builder.like(clienteRoot.get("nome"), "%" + filtro.getNome() + "%"));
		}

		if (!isEmpty(filtro.getCpfOuCnpj())) {
			predicates.add(builder.equal(clienteRoot.get("cpfOuCnpj"), filtro.getCpfOuCnpj()));
		}

		query.where(builder.and(predicates.toArray(new Predicate[] {})));

	}

}
