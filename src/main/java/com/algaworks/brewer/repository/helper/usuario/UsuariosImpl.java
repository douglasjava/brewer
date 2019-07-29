package com.algaworks.brewer.repository.helper.usuario;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Grupo;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.model.UsuarioGrupo;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil<Usuario> paginacaoUtil;

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario",
				String.class).setParameter("usuario", usuario).getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {

		List<Predicate> predicates = new ArrayList<>();
		List<Usuario> usuarios = null;

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> usuarioRoot = query.from(Usuario.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		adicionarFiltro(filtro, predicates, builder, usuarioRoot, query);

		paginacaoUtil.ordenacao(pageable, builder, query, usuarioRoot);
		
		usuarios = manager.createQuery(query)
						  .setFirstResult(primeiroRegistro)
						  .setMaxResults(totalRegistrosPorPagina)
						  .getResultList();

		return new PageImpl<>(usuarios, pageable, total(filtro));
		
	}

	private Long total(UsuarioFilter filtro) {
		List<Predicate> predicates = new ArrayList<>();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> usuarioRoot = query.from(Usuario.class);
		
		adicionarFiltro(filtro, predicates, builder, usuarioRoot, query);
		
		Integer total = manager.createQuery(query).getResultList().size();
		
		return total.longValue();
	}

	private void adicionarFiltro(UsuarioFilter filtro, List<Predicate> predicates, CriteriaBuilder builder, Root<Usuario> usuarioRoot, CriteriaQuery<Usuario> query) {
		
		if (!isEmpty(filtro.getNome())) {
			predicates.add(builder.like(usuarioRoot.get("nome"), "%" + filtro.getNome() + "%"));
		}

		if (!isEmpty(filtro.getEmail())) {
			predicates.add(builder.equal(usuarioRoot.get("sku"), filtro.getEmail()));
		}
			
		if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
			for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {				
				predicates.add(usuarioRoot.join("grupos").get("codigo").in(codigoGrupo));
			}
		}
		
	}

}
