package com.algaworks.brewer.repository.helper.cerveja;

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

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.dto.ValorItensEstoque;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import com.algaworks.brewer.storage.FotoStorage;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil<Cerveja> paginacaoUtil;
	
	@Autowired
	private FotoStorage fotoStorage;

	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {

		List<Predicate> predicates = new ArrayList<>();
		List<Cerveja> cervejas = null;

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> query = builder.createQuery(Cerveja.class);
		Root<Cerveja> cervejaRoot = query.from(Cerveja.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		adicionarFiltro(filtro, predicates, builder, cervejaRoot, query);

		paginacaoUtil.ordenacao(pageable, builder, query, cervejaRoot);
		
		cervejas = manager.createQuery(query)
						  .setFirstResult(primeiroRegistro)
						  .setMaxResults(totalRegistrosPorPagina)
						  .getResultList();

		return new PageImpl<>(cervejas, pageable, total(filtro));

	}
	
	
	@Override
	public ValorItensEstoque valorItensEstoque() {
		String query = "select new com.algaworks.brewer.dto.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
		return manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
	}

	private Long total(CervejaFilter filtro) {
		List<Predicate> predicates = new ArrayList<>();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cerveja> query = builder.createQuery(Cerveja.class);
		Root<Cerveja> cervejaRoot = query.from(Cerveja.class);
		
		adicionarFiltro(filtro, predicates, builder, cervejaRoot, query);
		
		Integer total = manager.createQuery(query).getResultList().size();
		
		return total.longValue();
	}
	
	private void adicionarFiltro(CervejaFilter filtro, List<Predicate> predicates, CriteriaBuilder builder, Root<Cerveja> cervejaRoot, CriteriaQuery<Cerveja> query) {
		
		if (filtro != null) {
			if (!isEmpty(filtro.getSku())) {
				predicates.add(builder.equal(cervejaRoot.get("sku"), filtro.getSku()));
			}

			if (!isEmpty(filtro.getNome())) {
				predicates.add(builder.like(cervejaRoot.get("nome"), "%" + filtro.getNome() + "%"));
			}

			if (isEstiloPresente(filtro)) {
				predicates.add(builder.and(builder.equal(cervejaRoot.get("estilo"), filtro.getEstilo())));
			}

			if (!isEmpty(filtro.getSabor())) {
				predicates.add(builder.and(builder.equal(cervejaRoot.get("sabor"), filtro.getSabor())));
			}

			if (!isEmpty(filtro.getOrigem())) {
				predicates.add(builder.and(builder.equal(cervejaRoot.get("origem"), filtro.getOrigem())));
			}

			if (!isEmpty(filtro.getValorDe())) {
				predicates.add(builder.and(builder.ge(cervejaRoot.get("valor"), filtro.getValorDe())));
			}

			if (!isEmpty(filtro.getValorAte())) {
				predicates.add(builder.and(builder.le(cervejaRoot.get("valor"), filtro.getValorAte())));
			}

		}
		
		query.where(builder.and(predicates.toArray(new Predicate[] {})));
	}

	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
	}

	@Override
	public List<CervejaDTO> porSkuOuNome(String skuOuNome) {
	
		String jpql = "select new com.algaworks.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) "
				+ "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
		
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql, CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome + "%")
				.getResultList();
		
		cervejasFiltradas.forEach(c -> c.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));
		
		return cervejasFiltradas;
	}

}
