package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exceptions.CidadeJaCadastradaException;
import com.algaworks.brewer.exceptions.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;

	@Transactional
	public void salvar(Cidade entity) {

		if(entity.isNovo()) {
			
			Optional<Cidade> cidadeExistente = cidades.findByNomeAndEstado(entity.getNome(), entity.getEstado());
			if (cidadeExistente.isPresent()) {
				throw new CidadeJaCadastradaException("Nome de cidade já cadastrado.");
			}
		}		

		cidades.save(entity);
	}
	
	@Transactional
	public void excluir(Cidade cidade) {
		try {
			this.cidades.delete(cidade);
			this.cidades.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cidade. O registro está sendo usado.");
		}
	}
}
