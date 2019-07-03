package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exceptions.CidadeJaCadastradaException;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;

	@Transactional
	public void salvar(Cidade entity) {

		Optional<Cidade> cidadeExistente = cidades.findByNomeAndEstado(entity.getNome(), entity.getEstado());
		if (cidadeExistente.isPresent()) {
			throw new CidadeJaCadastradaException("Nome de cidade já cadastrado.");
		}

		cidades.save(entity);
	}
}
